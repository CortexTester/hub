package com.cbx.hub.routing.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

//@JsonIgnoreProperties(ignoreUnknown = true)
data class PartyDto(
    var name: String,
    var url: String,
    var clientSidePartyId: String,
    var apiKey: String,
    var dialects: List<String>
)

data class RoutingEventDto(
    val senderId: Long,
    val receiverClientSidePartyId:Long,
    val contents: List<ContentInfo>,
    val dialectId: Long,
    val eventType: String,
    val trackingId: String
)

data class ContentInfo(
    val fileName: String,
    val fileType: String,
    val size: Long,
    val url: String
)
