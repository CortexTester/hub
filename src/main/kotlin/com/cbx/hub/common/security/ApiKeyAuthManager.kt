package com.cbx.hub.common.security

import com.cbx.hub.routing.service.RoutingService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication

class ApiKeyAuthManager(private val routingService: RoutingService) : AuthenticationManager {
    override fun authenticate(authentication: Authentication?): Authentication {
        val apiKey = authentication?.principal as String

        val party = routingService.getPartyByApiKey(apiKey)

        return if (party == null) {
            throw  BadCredentialsException("The API key is not matched")
        } else {
            PartyAuthenticationToken(party.toString(), apiKey, party, null)
        }
    }
}
