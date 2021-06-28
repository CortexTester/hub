package com.cbx.hub.routing.controller

import com.cbx.hub.routing.model.Party
import com.cbx.hub.routing.model.PartyDto
import com.cbx.hub.routing.service.RoutingService
import java.security.Principal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RoutingController {
    @Autowired
    lateinit var routingService: RoutingService

    @PostMapping("/routing/party")
//    fun routing(principal: Principal, @RequestBody party: PartyDto) : String { //todo: add principal
    fun routing( @RequestBody party: PartyDto) : String {

        return routingService.addPartyToRegistry(
            name = party.name,
            partyId = party.clientSidePartyId.toLong(),
            dialectIds = party.dialects.map { x->x.toLong() }.toMutableList(),
            url = party.url,
            apiKey = party.apiKey).toString()

//        val registryId = routingService.addPartyToRegistry("test party", 1001, 1, "http://localhost:8081", "testKey8" )
//        return "$registryId"
//        val eventId = eventService.addEvent(1, 2, "UBL2.0", 1, "http://aws/storage/1" )
//        val party = (principal as PartyAuthenticationToken)?.party
//        logger().info(party.toString())
//        return service.test() + " party registry id" + registryId
    }
}
