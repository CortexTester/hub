package com.cbx.hub.event.controller

import com.cbx.hub.event.model.MetadataDto
import javax.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class EventController {
    @PostMapping(value = ["/event"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun postMessage(
        @RequestPart(value = "files", required = true) files: Array<MultipartFile>,
        @RequestPart(value = "metadata", required = true) @Valid metadata: MetadataDto
    ) : String{
        println(files.size)
        println(metadata)
        return "event"
    }
}
