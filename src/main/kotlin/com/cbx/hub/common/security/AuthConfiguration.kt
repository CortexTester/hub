package com.cbx.hub.common.security

import com.cbx.hub.routing.service.RoutingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy


@Configuration
@EnableWebSecurity
class AuthConfiguration: WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var routingService: RoutingService
    private val API_KEY_AUTH_HEADER_NAME = "CBX_API_KEY"

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        val filter = ApiKeyAuthFilter(API_KEY_AUTH_HEADER_NAME)
        filter.setAuthenticationManager(ApiKeyAuthManager(routingService))
        http.csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(filter)
            .authorizeRequests()
            .anyRequest()
            .authenticated()
    }
}
