package com.cbx.hub.event.controller

import com.cbx.hub.common.security.PartyAuthenticationToken
import com.cbx.hub.common.storage.S3StorageService
import com.cbx.hub.event.model.ContentLocation
import com.cbx.hub.event.model.Event
import com.cbx.hub.event.model.MetadataDto
import com.cbx.hub.event.model.Receiver
import com.cbx.hub.event.repository.EventRepository
import com.cbx.hub.event.service.EventService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.security.Principal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class EventController(val eventService: EventService) {
    @PostMapping(value = ["/event"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun postMessage(
        principal: Principal,
        @RequestPart(value = "files", required = true) files: Array<MultipartFile>,
        @RequestPart(value = "metadata", required = true) @Valid metadata: MetadataDto
    ): String {
        val party = (principal as PartyAuthenticationToken)?.party

        eventService.processEvent(party.id, metadata, files)

        return "got event"
    }


}

