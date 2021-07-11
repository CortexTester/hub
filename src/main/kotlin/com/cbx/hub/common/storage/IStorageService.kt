package com.cbx.hub.common.storage

import java.io.InputStream
import org.springframework.http.MediaType
import org.springframework.stereotype.Service

@Service
interface IStorageService {
    fun uploadFile(keyName: String, contentType: String = "unknown", content: ByteArray): String
    fun getFile(keyName: String): ByteArray
}
