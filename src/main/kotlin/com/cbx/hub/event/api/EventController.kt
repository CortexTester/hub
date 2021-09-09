package com.cbx.hub.event.api

import com.cbx.hub.common.model.EventResponseDto

import com.cbx.hub.event.model.MetadataDto
import com.cbx.hub.event.service.EventService
import java.security.Principal
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
    ): EventResponseDto = eventService.processEvent(principal, metadata, files)

}

