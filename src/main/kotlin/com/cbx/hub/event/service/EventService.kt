package com.cbx.hub.event.service

import com.cbx.hub.event.model.Event
import com.cbx.hub.event.repository.EventRepository
import org.springframework.stereotype.Service

@Service
class EventService(val eventRepository: EventRepository) {
    fun test() = "from event service"
    fun addEvent(senderId: Long, receiverId: Long, dialect: String, eventTypeId: Int, contentLocation: String) : Long?{
        return 1L
       // return eventRepository.save(Event(senderId = senderId, receiverId = receiverId, dialect = dialect, eventTypeId = eventTypeId, contentLocation = contentLocation)).id
    }

//    @KafkaListener(topics = ["event"], groupId = "event-id",  containerFactory = "eventKafkaListenerContainerFactory")
//    fun eventHandler(event:EventMessage):Unit{
//        println("from event service $event")
//    }

}
