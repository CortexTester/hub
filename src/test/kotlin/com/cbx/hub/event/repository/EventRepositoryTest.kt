package com.cbx.hub.event.repository

import com.cbx.hub.IntegrationBaseTest
import com.cbx.hub.event.model.Event
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.transaction.annotation.Transactional

@Transactional
@AutoConfigureTestEntityManager
class EventRepositoryTest : IntegrationBaseTest() {
    @Autowired
    lateinit var eventRepository: EventRepository

    @Test
    fun `add event`(){
        val event = Event(
            senderId = 1L,
            receiverId = 2L,
            dialect = "UBL2",
            eventTypeId = 3,
            contentLocation = "https://aws.com"
        )

        eventRepository.saveAndFlush(event)

        assertThat(event.id).isEqualTo(1)

        val savedEvent = eventRepository.findById(event.id!!)

        assertThat(savedEvent.isPresent).isTrue
        assertThat(savedEvent.get().senderId).isEqualTo(event.senderId)
    }
}
