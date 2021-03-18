package svampyrerna.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import io.reactivex.Single
import io.swagger.v3.oas.annotations.tags.Tag
import svampyrerna.domain.TeamDTO
import svampyrerna.domain.TeamRebusDTO
import svampyrerna.domain.UnlockRequest
import svampyrerna.security.AuthHelper
import svampyrerna.services.RebusService
import java.util.*

@Tag(name = "Views")
@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/")
class ViewController(
    private val rebusService: RebusService,
) {

    @Produces(MediaType.TEXT_HTML)
    @Get
    @View("home")
    fun index(principal: Authentication?): Single<Map<String, Any>> {
        val data: MutableMap<String, Any> = HashMap()
        data["loggedIn"] = principal != null
        return if (principal != null) {
            val teamId = AuthHelper.getTeamId(principal)
            rebusService.rebuses(teamId)
                .toList()
                .map { rebus -> TeamRebusDTO(TeamDTO(teamId, principal.name), rebus) }
                .map { d -> data["data"] = d; data }
        } else {
            Single.just(data);
        }
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

    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post("/unlock")
    fun unlock(auth: Authentication, @Body unlockReq: UnlockRequest): Single<HttpResponse<*>> {
        val teamId = AuthHelper.getTeamId(auth)
        return rebusService.unlock(teamId, unlockReq.rebusId, unlockReq.unlockType)
            .map { HttpResponse.seeOther<Any>(UriBuilder.of("/").build()) }
    }
}