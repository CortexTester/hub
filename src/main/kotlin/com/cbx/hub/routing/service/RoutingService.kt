package com.cbx.hub.routing.service

import com.cbx.hub.routing.model.Dialect
import com.cbx.hub.routing.model.Party
import com.cbx.hub.routing.repository.DialectRepository
import com.cbx.hub.routing.repository.PartyRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class RoutingService(val partyRepository: PartyRepository, val dialectRepository: DialectRepository) {
    fun addPartyToRegistry(name: String, partyId: Long, dialectIds: MutableList<Long>, url: String, apiKey: String): Long? {
        try {
            val dialect: Dialect = dialectRepository.getOne(dialectIds.first()) //todo: handle multi dialects
            var party = Party(
                name = name,
                url = url,
                clientSidePartyId = partyId,
                apiKey = apiKey,
                dialects = mutableListOf(dialect)
            )
            partyRepository.save(party)
            return party.id

        }catch (e: Exception){
            println(e)
        }
        return null
    }
//    @KafkaListener(topics = ["event"], groupId = "routing-id", containerFactory = "eventKafkaListenerContainerFactory")
//    fun eventHandler(event: EventMessage): Unit {
//        println("from routing service $event")
//    }

//    @Cacheable(value = ["party"], key = "{#key, #clientSidePartyId}", unless = "#result!=null")
    fun getPartyByApiKey(key: String, clientSidePartyId: Long = 1) =
        partyRepository.findByApiKeyAndClientSidePartyId(key, clientSidePartyId)

}
