package com.cbx.hub.routing.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

//@JsonIgnoreProperties(ignoreUnknown = true)
data class PartyDto(
    var name: String,
    var url: String,
    var clientSidePartyId: String,
    var apiKey:String,
    var dialects: List<String>
)
