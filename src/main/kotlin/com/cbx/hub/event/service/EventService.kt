package com.cbx.hub.event.service

import com.cbx.hub.common.storage.S3StorageService
import com.cbx.hub.event.model.ContentLocation
import com.cbx.hub.event.model.Event
import com.cbx.hub.event.model.MetadataDto
import com.cbx.hub.event.model.Receiver
import com.cbx.hub.event.repository.EventRepository
import com.cbx.hub.routing.service.RoutingService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class EventService(val storageService: S3StorageService, val eventRepository: EventRepository, val routingService: RoutingService) {

    //this maybe performance issue
    fun processEvent(senderId: Long, metadata: MetadataDto, files: Array<MultipartFile>){
        var contentLocations = mutableListOf<ContentLocation>()
        var receivers = mutableListOf<Receiver>()
        var event = Event(
            senderId = senderId,
            dialectId = metadata.dialectId,
            eventType = metadata.eventType,
            trackingId = metadata.trackingId
        )

        //store files
        val folder = getS3FolderName(senderId)
        val metaDataFileName = "metaData.json"
        val metaDataUrl = storageService.uploadFile(
            getS3Key(folder, metaDataFileName),
            MediaType.APPLICATION_JSON.toString(),
            jacksonObjectMapper().writeValueAsBytes(metadata)
        )
        files.forEach { file ->
            val fileName = file.originalFilename ?: UUID.randomUUID().toString()
            val contentType = file.contentType ?: "unknown"
            val keyName = getS3Key(folder, fileName)
            val url = storageService.uploadFile(keyName, contentType, file.bytes)
            contentLocations.add(
                ContentLocation(
                    fileName = fileName,
                    url = url,
                    size = file.size,
                    fileType = contentType,
                    event = event
                )
            )
        }

        //save event to db
        metadata.receiverIds.forEach { x ->
            receivers.add(Receiver(partyId = x, event = event))
        }
        event.contents = contentLocations
        event.receiverIds = receivers
        eventRepository.save(event)

        //call routing
        //todo: if receiver ids is [0], should invoke public service
        routingService.routingEvent(event)
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
