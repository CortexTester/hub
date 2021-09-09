package com.cbx.hub.event.model

import com.cbx.hub.common.model.ContentResponseDto
import com.cbx.hub.common.model.ContentStorageDto
import com.cbx.hub.common.model.EventDto
import com.cbx.hub.common.model.EventResponseDto
import com.cbx.hub.common.model.RoutingEventDto
import com.cbx.hub.routing.model.Party
import com.cbx.hub.routing.model.PartyDto

fun Party.toDto(hideApiKey:Boolean = true) = PartyDto(
    name = this.name,
    url = this.url,
    clientSidePartyId = this.clientSidePartyId,
    dialects = this.dialects.map { it.name },
    apiKey = if (hideApiKey) {""} else {this.apiKey}
)

fun Event.toDto(senderName: String, receivers: MutableList<PartyDto>, contents: MutableList<ContentStorageDto>) = EventDto(
    senderId = this.senderId,
    senderName = senderName,
    receivers = receivers,
    dialectId = this.dialectId,
    eventType = this.eventType,
    action = this.action,
    trackingId = this.trackingId,
    contents = contents
)

fun ContentLocation.toContentResponseDto() = ContentResponseDto(
    locationId = this.id!!,
    fileName = this.fileName,
    fileType = this.fileType,
    size = this.size
)

fun ContentStorageDto.toEntity(event: Event) = ContentLocation(
    fileName = this.fileName,
    fileType = this.fileType,
    url = this.url,
    size = this.size,
    event = event
)

fun EventDto.toRoutingEventDto(receiverClientSidePartyId: Long?) = RoutingEventDto(
    senderId = this.senderId,
    senderName = this.senderName,
    receiverClientSidePartyId = receiverClientSidePartyId ?: 0,
    contents = this.contents,
    dialectId = this.dialectId,
    eventType = this.eventType,
    action = this.action,
    trackingId = this.trackingId
)

fun Event.toEventResponseDto() = EventResponseDto(
    trackingId = this.trackingId,
    contents = this.contents.map { it.toContentResponseDto() }.toMutableList()
)
