package com.cbx.hub.routing.service

import com.cbx.hub.common.model.EventDto
import com.cbx.hub.common.utils.logger
import com.cbx.hub.event.model.toRoutingEventDto
import com.cbx.hub.routing.model.Dialect
import com.cbx.hub.routing.model.Party
import com.cbx.hub.routing.repository.DialectRepository
import com.cbx.hub.routing.repository.PartyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class RoutingService(
    val partyRepository: PartyRepository,
    val dialectRepository: DialectRepository,
    val restTemplate: RestTemplate
) {
    fun addPartyToRegistry(
        name: String,
        partyId: Long,
        dialectIds: MutableList<Long>,
        url: String,
        apiKey: String
    ): Long {
        val dialect: Dialect = dialectRepository.getById(dialectIds.first()) //todo: handle multi dialects
        var party = Party(
            name = name,
            url = url,
            clientSidePartyId = partyId,
            apiKey = apiKey,
            dialects = mutableListOf(dialect)
        )
        partyRepository.save(party)
        return party.id
    }

    fun getPartyById(partyId: Long): Party? = partyRepository.findByIdOrNull(partyId)

//    fun routingEvent(event: Event) {
//        logger().info("routing $event")
//        event.receiverIds.forEach { x ->
//            val receiverParty = partyRepository.getById(x.partyId)
//            logger().info("send to ${receiverParty.name} with url ${receiverParty.url}")
//            postForEntity(receiverParty.url, receiverParty.clientSidePartyId, event)
//        }
//    }

    //    @EventListener
    fun eventDtoHandler(eventDto: EventDto) {
        logger().info("routing $eventDto")
        eventDto.receivers.forEach { receiverParty ->
            logger().info("send to ${receiverParty.name} with url ${receiverParty.url}")
//            postForEntity(receiverParty.url, receiverParty.clientSidePartyId, eventDto)
        }
    }

    private fun postForEntity(
        url: String,
        receiverClientSidePartyId: Long?,
        eventDto: EventDto
    ): ResponseEntity<String> =
        restTemplate.postForEntity(
            UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/event")
                .build()
                .toUri(),
            eventDto.toRoutingEventDto(receiverClientSidePartyId),
            String::class.java
        )


//    private fun mapRoutingEventDto(receiverClientSidePartyId: Long?, event: Event): RoutingEventDto {
//        return RoutingEventDto(
//            senderId = event.senderId,
//            receiverClientSidePartyId = receiverClientSidePartyId ?: 0,
//            contents = event.contents.map { x -> ContentStorageDto(x.fileName, x.fileType, x.size, x.url) },
//            eventType = event.eventType,
//            dialectId = event.dialectId,
//            trackingId = event.trackingId
//        )
//    }


//    @KafkaListener(topics = ["event"], groupId = "routing-id", containerFactory = "eventKafkaListenerContainerFactory")
//    fun eventHandler(event: EventMessage): Unit {
//        println("from routing service $event")
//    }

    //    @Cacheable(value = ["party"], key = "{#key, #clientSidePartyId}", unless = "#result!=null")
    fun getPartyByApiKey(key: String, clientSidePartyId: Long = 1) =
        partyRepository.findByApiKeyAndClientSidePartyId(key, clientSidePartyId)

}
