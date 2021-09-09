package com.cbx.hub.common.model

import com.cbx.hub.routing.model.PartyDto

interface Event {
    var senderId: Long
    var senderName: String
    var dialectId: Long
    var eventType: String
    var action: String
    var trackingId: String
    var contents: MutableList<ContentStorageDto>
}

interface Content {
    var fileName: String
    var fileType: String
    var size: Long
}

data class EventDto(
    override var senderId: Long,
    override var senderName: String,
    override var dialectId: Long,
    override var eventType: String,
    override var action: String,
    override var trackingId: String,
    override var contents: MutableList<ContentStorageDto>,
    var receivers: MutableList<PartyDto>,
) : Event

data class RoutingEventDto(
    override var senderId: Long,
    override var senderName: String,
    override var dialectId: Long,
    override var eventType: String,
    override var action: String,
    override var trackingId: String,
    override var contents: MutableList<ContentStorageDto>,
    val receiverClientSidePartyId: Long,
) : Event

data class EventResponseDto(
    var trackingId: String,
    var contents: MutableList<ContentResponseDto>
)

data class ContentResponseDto(
    override var fileName: String,
    override var fileType: String,
    override var size: Long,
    var locationId: Long,
) : Content

data class ContentStorageDto(
    override var fileName: String,
    override var fileType: String,
    override var size: Long,
    val url: String
): Content

