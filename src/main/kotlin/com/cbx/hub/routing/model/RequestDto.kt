package com.cbx.hub.routing.model

data class PartyDto(
    var name: String,
    var url: String,
    var clientSidePartyId: Long?,
    var apiKey: String,
    var dialects: List<String>
)

