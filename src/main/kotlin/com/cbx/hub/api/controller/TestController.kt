package com.cbx.hub.api.controller

import com.cbx.hub.routing.service.RoutingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @Autowired
    lateinit var routingService: RoutingService

    @GetMapping("/test")
    fun test():String{
        return "test hub"
    }

}
