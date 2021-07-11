package com.cbx.hub.common.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType

inline fun <reified T> T.logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}


