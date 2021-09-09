package com.cbx.hub.event.repository

import com.cbx.hub.IntegrationBaseTest
import com.cbx.hub.event.model.ContentLocation
import com.cbx.hub.event.model.Event
import com.cbx.hub.event.model.Receiver
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.transaction.annotation.Transactional

@Transactional("eventTransactionManager")
@AutoConfigureTestEntityManager
class EventRepositoryTest : IntegrationBaseTest() {
    @Autowired
    lateinit var eventRepository: EventRepository

    @Test
    fun `add event`() {

        val event = Event(
            senderId = 1L,
            dialectId = 1L,
            eventType = "CreatedInvoice",
            trackingId = "trackingId-111"
        )
        val receivers = mutableListOf<Receiver>(
            Receiver(partyId = 1, event = event),
            Receiver(partyId = 21, event = event)
        )
        val locations = mutableListOf<ContentLocation>(
            ContentLocation(fileName = "test.pdf", url = "http:aws.com/test/111-test.pdf", size = 10, event = event, fileType ="pdf"),
            ContentLocation(fileName = "main.json", url = "http:aws.com/test/111-main.pdf", size = 10, event = event, fileType = "json")
        )
        event.receiverIds = receivers
        event.contents = locations

        eventRepository.saveAndFlush(event)

        assertThat(event.id).isEqualTo(1)

        val savedEvent = eventRepository.findById(event.id!!)

        assertThat(savedEvent.isPresent).isTrue
        assertThat(savedEvent.get().senderId).isEqualTo(event.senderId)
        assertThat(savedEvent.get().contents.get(0).fileName).isEqualTo("test.pdf")
    }
}
