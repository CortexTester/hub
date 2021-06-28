package com.cbx.hub.routing.repository

import com.cbx.hub.IntegrationBaseTest
import com.cbx.hub.routing.model.Dialect
import com.cbx.hub.routing.model.Party
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@Transactional
@AutoConfigureTestEntityManager
class PartyRepositoryTest : IntegrationBaseTest() {

    @Autowired
    lateinit var partyRepository: PartyRepository

    @Autowired
    lateinit var dialectRepository: DialectRepository

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Test
    fun `test party`() {
        var party = Party(name = "party01", url = "https://cbx.com", clientSidePartyId = 1L, apiKey = "testKey")
        partyRepository.saveAndFlush(party)
        val p = partyRepository.findByIdOrNull(party.id!!)
        assertThat(p).isEqualTo(party)
    }

    @Test
    fun `test party and dialect`() {
        var dialect1 = Dialect(name = "cbx")
        var dialect2 = Dialect(name = "pidx")
        entityManager.persist(dialect1)
        entityManager.persist(dialect2)
        entityManager.flush()
        var party = Party(
            name = "party01",
            url = "https://cbx.com",
            clientSidePartyId = 1L,
            apiKey = "testKey",
            dialects = mutableListOf(dialect1, dialect2)
        )
        entityManager.persist(party)
        entityManager.flush()
        val p = partyRepository.findByIdOrNull(party.id!!)
        assertThat(p).isEqualTo(party)
    }
}
