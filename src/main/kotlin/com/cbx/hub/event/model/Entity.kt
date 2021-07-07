package com.cbx.hub.event.model

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
data class Event(
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long? = null,
    var senderId: Long,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "event")
    var receiverIds: MutableList<Receiver> = mutableListOf(),
    var dialect: String,
    var eventType: String,
    var trackingId: String,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "event")
    var contents: MutableList<ContentLocation> = mutableListOf()
)

@Entity
data class Receiver(
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long? = null,
    var partyId: Long,
    @ManyToOne
    var event: Event
)

@Entity
data class ContentLocation(
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long? = null,
    var fileName: String,
    var url:String,
    var size:Long,
    @ManyToOne
    var event: Event
)


