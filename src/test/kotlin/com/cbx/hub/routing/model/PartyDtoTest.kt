//package com.cbx.hub.routing.model
//
//import com.cbx.hub.routing.model.PartyDto
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.json.JsonTest
//import org.springframework.boot.test.json.JacksonTester
//
//@JsonTest
//class PartyDtoTest {
//    @Autowired
//    lateinit var jacksonTester: JacksonTester<PartyDto>
//
//    @Test
//    fun `partyDto as request`(){
//        val party = PartyDto(
//            name = "test party",
//            clientSidePartyId = "1",
//            apiKey = "test api key",
//            url = "https://test.com",
//            dialects = mutableListOf("1")
//        )
//
//        val result = jacksonTester.write(party)
//
//        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("test party");
//    }
//}
