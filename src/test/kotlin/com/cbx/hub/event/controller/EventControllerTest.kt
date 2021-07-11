//package com.cbx.hub.event.controller
//
//import com.cbx.hub.IntegrationBaseTest
//import java.io.IOException
//import java.net.URI
//import java.nio.file.Files
//import java.nio.file.Path
//import org.assertj.core.api.Assertions
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.web.client.TestRestTemplate
//import org.springframework.core.io.FileSystemResource
//import org.springframework.core.io.Resource
//import org.springframework.http.HttpEntity
//import org.springframework.http.HttpHeaders
//import org.springframework.http.HttpStatus
//import org.springframework.http.MediaType
//import org.springframework.util.LinkedMultiValueMap
//import org.springframework.util.MultiValueMap
//import org.springframework.web.client.RestTemplate
//
//
//class EventControllerTest : IntegrationBaseTest() {
//    @Autowired
//    lateinit var restTemplate: TestRestTemplate
//
//    @Test
//    fun `Testing Book endpoint`() {
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.MULTIPART_FORM_DATA
//        headers.add("CBX_API_KEY", "TestKey1")
//
//        val body: MultiValueMap<String, Any> = LinkedMultiValueMap()
//        body.add("files", getTestFile("test1", ".pdf", "test"))
//        body.add(
//            "metadata", getTestFile(
//                "metadata", ".json", """
//                    {
//                        "senderId": 1,
//                        "receiverIds": [2, 3],
//                        "dialectId":1,
//                        "eventType": "test",
//                        "trackingId": "testTrackingId"
//                    }
//                    """.trimIndent()
//            )
//        )
//        body.add("files", getTestFile("main", ".json", " test"))
//
//        val requestEntity: HttpEntity<MultiValueMap<String, Any>> = HttpEntity(body, headers)
//        val response = restTemplate.postForEntity(URI("/event"), requestEntity, String::class.java)
//
//        println("Response code: " + response.statusCode)
//
//        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
//        Assertions.assertThat(response.body).contains("event")
//    }
//
//    @Throws(IOException::class)
//    fun getTestFile(filename: String, extentName: String, content: String): Resource? {
//        val testFile: Path = Files.createTempFile(filename, extentName)
//        println("Creating and Uploading Test File: $testFile")
//        Files.write(testFile, content.toByteArray())
//        return FileSystemResource(testFile.toFile())
//    }
//}
