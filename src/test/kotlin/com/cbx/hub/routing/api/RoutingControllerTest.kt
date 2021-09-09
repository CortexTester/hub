//package com.cbx.hub.routing.controller
//
//import com.cbx.hub.IntegrationBaseTest
//import com.cbx.hub.routing.model.Dialect
//import com.cbx.hub.routing.model.PartyDto
//import com.cbx.hub.routing.repository.DialectRepository
//import com.cbx.hub.routing.repository.PartyRepository
//import java.net.URI
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.web.client.TestRestTemplate
//import org.springframework.http.HttpEntity
//import org.springframework.http.HttpHeaders
//import org.springframework.http.HttpMethod
//import org.springframework.http.HttpStatus
//
////@Transactional("routingTransactionManager")
////@AutoConfigureTestEntityManager
//
//class RoutingControllerTest() : IntegrationBaseTest() {
//
//    @Autowired
//    lateinit var restTemplate: TestRestTemplate
//
//    @Autowired
//    lateinit var partyRepository: PartyRepository
//
//    @Autowired
//    lateinit var dialectRepository: DialectRepository
//
//    @BeforeEach
//    fun seetup(){
//
//    }
//
//    @Test
//    fun `test endpoint`() {
//        val headers = HttpHeaders()
//        headers.add("CBX_API_KEY", "TestKey1")
//        val entity = restTemplate.exchange(URI("/test"), HttpMethod.GET, HttpEntity<Any>(headers), String::class.java)
//        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
//        assertThat(entity.body).contains("hub")
//    }
//
//    @Test
//    fun `add party`() {
//        dialectRepository.saveAndFlush(Dialect(id = 1L, name = "UBL2"))
//
//        val party = PartyDto(
//            name = "test party",
//            clientSidePartyId = "1",
//            apiKey = "test api key",
//            url = "https://test.com",
//            dialects = mutableListOf("1")
//        )
//        val headers = HttpHeaders()
//        headers.add("CBX_API_KEY", "TestKey1")
//
//        val request = HttpEntity<PartyDto>(party, HttpHeaders(headers))
//
//        var result = restTemplate.postForEntity(URI("/routing/party"), request, String::class.java)
//
//        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
//
//        var partyInDb = partyRepository.findByApiKeyAndClientSidePartyId(party.apiKey, party.clientSidePartyId.toLong())
//
//        assertThat(partyInDb?.name).isEqualTo(party.name)
//
//    }
//
//}
