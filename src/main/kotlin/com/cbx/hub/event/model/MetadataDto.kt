package com.cbx.hub.event.model

data class MetadataDto(
    val senderId: Long, //todo: removed, should get senderId from principle
    val receiverIds: List<Long>,
    val dialectId: Long, //may be add doc type and action. default document name is main.json
    val eventType: String,
    val trackingId: String
)
