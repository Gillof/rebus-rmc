package svampyrerna.controllers

import io.micronaut.context.annotation.Value
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.scheduling.annotation.Scheduled
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import java.util.*

@Tag(name = "Views")
@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/")
class ViewController(
    @Client(id = "ping") private val pingClient: HttpClient,
    @Value("\${ping.enabled:false}") private val pingEnabled: Boolean
) {
    private val LOG = LoggerFactory.getLogger(ViewController::class.java)

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

    @Scheduled(fixedRate = "10m")
    fun everyFiveMinutes() {
        if (pingEnabled) {
            LOG.info("Pinging /health to ensure uptime")
            val response = pingClient.toBlocking().exchange<Any>("/health")
            LOG.info("Got ${response.status} from /health")
        } else {
            LOG.debug("Ping disabled")
        }
    }
}