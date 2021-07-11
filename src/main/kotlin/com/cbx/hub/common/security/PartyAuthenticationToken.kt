package com.cbx.hub.common.security

import com.cbx.hub.routing.model.Party
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

data class PartyAuthenticationToken(
    val principal: String,
    val credential: String,
    val party: Party,
    val roles: MutableCollection<out GrantedAuthority>?
) : AbstractAuthenticationToken(
    roles
) {
    init {
        super.setDetails(party)
        isAuthenticated = true
    }
    override fun getCredentials(): Any {
        return credentials
    }

    override fun getPrincipal(): Any {
        return principal
    }
}
