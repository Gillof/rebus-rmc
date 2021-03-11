package svampyrerna.controllers

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import java.util.*

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/")
class ViewController {
    @Produces(MediaType.TEXT_HTML)
    @Get
    @View("home")
    fun index(principal: Authentication?): Map<String, Any> {
        val data: MutableMap<String, Any> = HashMap()
        data["loggedIn"] = principal != null
        if (principal != null) {
            data["username"] = principal.name
        }
        return data
    }

    @Produces(MediaType.TEXT_HTML)
    @Get("/auth")
    @View("auth")
    fun auth(): Map<String, Any> {
        return HashMap()
    }

    @Produces(MediaType.TEXT_HTML)
    @Get("/authFailed")
    @View("auth")
    fun authFailed(): Map<String, Any> {
        return Collections.singletonMap<String, Any>("errors", true)
    }
}