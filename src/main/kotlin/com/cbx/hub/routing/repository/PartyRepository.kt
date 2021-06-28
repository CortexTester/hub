package com.cbx.hub.routing.repository

import com.cbx.hub.routing.model.Dialect
import com.cbx.hub.routing.model.Party
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PartyRepository: JpaRepository<Party, Long> {
    fun findByApiKeyAndClientSidePartyId(apiKey: String, clientSidePartyId: Long) : Party?
}

@Repository
interface DialectRepository: JpaRepository<Dialect, Long> {}
