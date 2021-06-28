package com.cbx.hub.event.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Event(
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Long? = null,
    var senderId: Long,
    var receiverId: Long,
    var dialect: String,
    var eventTypeId: Int,
    var contentLocation: String //todo: multi contents
)
