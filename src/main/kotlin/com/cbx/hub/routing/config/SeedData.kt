package com.cbx.hub.routing.config

import com.cbx.hub.routing.model.Dialect
import com.cbx.hub.routing.model.Party
import com.cbx.hub.routing.repository.DialectRepository
import com.cbx.hub.routing.repository.PartyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class SeedData: ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    lateinit var partyRepository: PartyRepository

    @Autowired
    lateinit var dialectRepository: DialectRepository

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        seedData()
    }

    private fun seedData() {
        var dialect1 = Dialect(name = "cbx")
        var dialect2 = Dialect(name = "pidx")

        var party1 = Party(
            name = "web party 1",
            url = "http://localhost:8091",
            clientSidePartyId = 1L,
            apiKey = "TestKey1",
            dialects = mutableListOf(dialect1, dialect2)
        )
        dialect1.parties.add(party1)
        dialect2.parties.add(party1)

        var party2 = Party(
            name = "integration party 1",
            url = "http://localhost:8092",
            clientSidePartyId = 1L,
            apiKey = "TestKey2",
            dialects = mutableListOf(dialect1, dialect2)
        )
        dialect1.parties.add(party2)
        dialect2.parties.add(party2)

        var party3 = Party(
            name = "web party 2",
            url = "http://localhost:8091",
            clientSidePartyId = 2L,
            apiKey = "TestKey3",
            dialects = mutableListOf(dialect1, dialect2)
        )
        dialect1.parties.add(party2)
        dialect2.parties.add(party2)

        dialectRepository.save(dialect1)
        dialectRepository.save(dialect2)
        partyRepository.save(party1)
        partyRepository.save(party2)
        partyRepository.save(party3)

    }
}
