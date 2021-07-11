package com.cbx.hub.routing.repository

import com.cbx.hub.IntegrationBaseTest
import com.cbx.hub.routing.model.Dialect
import com.cbx.hub.routing.model.Party
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@Transactional("routingTransactionManager")
@AutoConfigureTestEntityManager
class PartyRepositoryTest : IntegrationBaseTest() {

    @Autowired
    lateinit var partyRepository: PartyRepository

    @Autowired
    lateinit var dialectRepository: DialectRepository

    @Test
//    @Sql("/seed-data.sql")
    fun `test party`() {
        var party = Party(name = "party01", url = "https://cbx.com", clientSidePartyId = 1L, apiKey = "testKey")
        partyRepository.saveAndFlush(party)
        val p = partyRepository.findByIdOrNull(party.id)
        assertThat(p).isEqualTo(party)
    }

    @Test
    fun `test party and dialect`() {
        var dialect1 = Dialect(name = "cbx")
        var dialect2 = Dialect(name = "pidx")
        dialectRepository.saveAndFlush(dialect1)
        dialectRepository.saveAndFlush(dialect2)
        var party = Party(
            name = "party01",
            url = "https://cbx.com",
            clientSidePartyId = 1L,
            apiKey = "testKey",
            dialects = mutableListOf(dialect1, dialect2)
        )
        partyRepository.saveAndFlush(party)
        val p = partyRepository.findByIdOrNull(party.id)
        assertThat(p).isEqualTo(party)
    }
}
