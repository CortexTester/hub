package com.cbx.hub.event.service

import com.cbx.hub.common.helper.ResourceNotFoundException
import com.cbx.hub.common.model.ContentStorageDto
import com.cbx.hub.common.model.EventResponseDto
import com.cbx.hub.common.security.PartyAuthenticationToken
import com.cbx.hub.common.storage.S3StorageService
import com.cbx.hub.common.utils.EventMetadata
import com.cbx.hub.event.model.Event

import com.cbx.hub.event.model.MetadataDto
import com.cbx.hub.event.model.Receiver
import com.cbx.hub.event.model.toDto
import com.cbx.hub.event.model.toEntity
import com.cbx.hub.event.model.toEventResponseDto
import com.cbx.hub.event.repository.EventRepository
import com.cbx.hub.routing.model.PartyDto
import com.cbx.hub.routing.service.RoutingService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.security.Principal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class EventService(
    val storageService: S3StorageService,
    val eventRepository: EventRepository,
    val routingService: RoutingService,
//    val publisher: ApplicationEventPublisher
) {

    //this maybe performance issue. may need to use async event pushier
    fun processEvent(principal: Principal, metadata: MetadataDto, files: Array<MultipartFile>) : EventResponseDto {
        val sender = (principal as PartyAuthenticationToken)?.party
        var receivers = mutableListOf<Receiver>()
        var partyDtos = mutableListOf<PartyDto>()
        var contentStorageDtos = mutableListOf<ContentStorageDto>()

        //1. validate receivers is existing
        metadata.receiverIds.forEach { x ->
            val party = routingService.getPartyById(x) ?: throw ResourceNotFoundException("can not find receiver $x")
            partyDtos.add(party.toDto())
        }
        //2. save files to s3
        val folder = getS3FolderName(sender.id)
        val metadataUrl = saveMetadataToStorage(folder, metadata)
        val metadataContentStorageDto = ContentStorageDto(
            fileName = EventMetadata.name,
            url = metadataUrl,
            fileType = MediaType.APPLICATION_JSON_VALUE,
            size = metadata.toString().length.toLong()
        )
        contentStorageDtos.add(metadataContentStorageDto)
        files.forEach { file ->
            val fileName = file.originalFilename ?: UUID.randomUUID().toString()
            val fileType = file.contentType ?: "unknown"
            val keyName = getS3Key(folder, fileName)
            val url = storageService.uploadFile(keyName, fileType, file.bytes)
            contentStorageDtos.add(
                ContentStorageDto(
                    fileName = fileName,
                    fileType = fileType,
                    size = file.size,
                    url = url
                )
            )
        }
        //3. map incoming request  entity and save
        var event = Event(
            senderId = sender.id,
            dialectId = metadata.dialectId,
            eventType = metadata.eventType,
            trackingId = metadata.trackingId,
            action = metadata.action
        )
        //get receiver parties
        metadata.receiverIds.forEach { x ->
            receivers.add(Receiver(partyId = x, event = event))
        }
        event.receiverIds = receivers
        event.contents = contentStorageDtos.filter { x->x.fileName != EventMetadata.name }.map { it.toEntity(event) }.toMutableList()
        eventRepository.save(event)

        //4. map request to eventDto,
        val eventDto = event.toDto(sender.name, partyDtos, contentStorageDtos)

        //5. fire event -- invoke routingService, it throws error when exception occur
        //publisher.publishEvent(eventDto)
        routingService.eventDtoHandler(eventDto)

        //6. return eventResponseDto
        return event.toEventResponseDto()

    }

    private fun saveMetadataToStorage(folder: String, metadata: MetadataDto): String {
        return storageService.uploadFile(
            getS3Key(folder, EventMetadata.name),
            MediaType.APPLICATION_JSON.toString(),
            jacksonObjectMapper().writeValueAsBytes(metadata)
        )
    }

    private fun getS3FolderName(partyId: Long): String {
        val today = LocalDate.now().toString()
        val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("H-m-ss"))
        return "$partyId/$today/$time"
    }

    private fun getS3Key(folder: String, fileName: String): String {
        val cleanFileName = removeSpecialCharInFileName(fileName)
        return "$folder/$cleanFileName"
    }

    private fun removeSpecialCharInFileName(fileName: String): String {
        val regex = Regex("[^A-Za-z0-9._-]")
        return regex.replace(fileName, "")
    }


//    @KafkaListener(topics = ["event"], groupId = "event-id",  containerFactory = "eventKafkaListenerContainerFactory")
//    fun eventHandler(event:EventMessage):Unit{
//        println("from event service $event")
//    }

}
