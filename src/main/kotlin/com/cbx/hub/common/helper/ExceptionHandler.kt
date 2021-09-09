package com.cbx.hub.common.helper

import java.util.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFoundException(ex: ResourceNotFoundException, request: WebRequest): ResponseEntity<ErrorMessage?>? {
        val message = ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            Date(),
            ex.message!!,
            request.getDescription(false)
        )
        return ResponseEntity(message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun globalExceptionHandler(ex: Exception, request: WebRequest): ResponseEntity<ErrorMessage?>? {
        val message = ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Date(),
            ex.message!!,
            request.getDescription(false)
        )
        return ResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
data class ErrorMessage(
    var statusCode: Int,
    var data: Date,
    var message: String,
    var description: String?
)

class ResourceNotFoundException(message: String) : RuntimeException(message)
