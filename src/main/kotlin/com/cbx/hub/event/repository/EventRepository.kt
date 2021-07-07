package com.cbx.hub.event.repository

import com.cbx.hub.event.model.ContentLocation
import com.cbx.hub.event.model.Event
import com.cbx.hub.event.model.Receiver
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : JpaRepository<Event, Long> {
}

@Repository
interface ReceiverRepository : JpaRepository<Receiver, Long> {
}

@Repository
interface ContentLocationRepository : JpaRepository<ContentLocation, Long> {
}
