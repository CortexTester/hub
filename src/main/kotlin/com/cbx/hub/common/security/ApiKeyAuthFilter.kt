package com.cbx.hub.common.security

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletRequest

class ApiKeyAuthFilter (private val headerName:String) : AbstractPreAuthenticatedProcessingFilter(){
    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest?): String? {
        return request?.getHeader(headerName)
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest?): Any? {
        return null
    }
}
