package com.cbx.hub

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@DataJpaTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationBaseTest {
    companion object {
        @Container
        private val routingSQLContainer = PostgreSQLContainer<Nothing>("postgres:latest")

        @Container
        private val eventSQLContainer = PostgreSQLContainer<Nothing>("postgres:latest")


        @DynamicPropertySource
        @JvmStatic
        fun registerRoutingDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("routing.datasource.url", routingSQLContainer::getJdbcUrl)
            registry.add("routing.datasource.username", routingSQLContainer::getUsername)
            registry.add("routing.datasource.password", routingSQLContainer::getPassword)
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerEventDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("event.datasource.url", eventSQLContainer::getJdbcUrl)
            registry.add("event.datasource.username", eventSQLContainer::getUsername)
            registry.add("event.datasource.password", eventSQLContainer::getPassword)
        }
    }
}
