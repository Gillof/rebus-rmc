package rally.montecarl.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.micronaut.views.View
import io.swagger.v3.oas.annotations.tags.Tag
import rally.montecarl.controllers.ViewController.ViewController.redirectToAdmin
import rally.montecarl.domain.*
import rally.montecarl.repositories.*
import rally.montecarl.security.AuthHelper
import rally.montecarl.services.RebusService
import java.util.*

@Tag(name = "Views")
@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/")
class ViewController(
    private val rebusService: RebusService,
    private val teamsRepository: TeamsRepository,
    private val rebusRepository: RebusRepository,
    private val unlockRepository: UnlockRepository
) {

    @Produces(MediaType.TEXT_HTML)
    @Get
    @View("home")
    fun index(auth: Authentication?): Map<String, Any> {
        val data: MutableMap<String, Any> = HashMap()
        data["loggedIn"] = auth != null
        return if (auth != null) {
            val teamId = AuthHelper.getTeamId(auth)
            TeamRebusDTO(TeamDTO(teamId, auth.name, AuthHelper.getAdminRole(auth)), rebusService.rebuses(teamId))
                .let { d -> data["data"] = d; data }
        } else {
            data
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
    fun unlock(auth: Authentication, @Body unlockReq: UnlockRequest): HttpResponse<Any> {
        val teamId = AuthHelper.getTeamId(auth)
        rebusService.unlock(teamId, unlockReq.rebusId, unlockReq.unlockType)
        return HttpResponse.seeOther<Any>(UriBuilder.of("/").build())
    }

    @Produces(MediaType.TEXT_HTML)
    @Get("/admin")
    @View("admin")
    @Secured("ADMIN")
    fun admin(auth: Authentication): Map<String, Any> {
        return mapOf(
            "data" to rebusService.getTeamOverview().toList(),
            "rebusList" to rebusRepository.findAll().toList(),
            "teamList" to teamsRepository.findAll().toList()
        )
    }

    @Post("/admin/rebus")
    @Secured("ADMIN")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    fun newRebus(auth: Authentication, @Body newRebusRequest: NewRebusRequest): HttpResponse<Any> {
        rebusRepository.save(
            RebusEntity(
                id = UUID.randomUUID(),
                nr = newRebusRequest.nr,
                hint = newRebusRequest.hint,
                answer = newRebusRequest.answer
            )
        )
        return redirectToAdmin()
    }

    @Post("/admin/rebus/delete")
    @Secured("ADMIN")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    fun deleteRebus(auth: Authentication, @Body deleteRebusRequest: DeleteRebusRequest): HttpResponse<Any> {
        unlockRepository.deleteByRebusId(deleteRebusRequest.rebusId)
        rebusRepository.deleteById(deleteRebusRequest.rebusId)
        return redirectToAdmin()
    }

    @Post("/admin/team")
    @Secured("ADMIN")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    fun newTeam(auth: Authentication, @Body newTeamRequest: NewTeamRequest): HttpResponse<Any> {
        teamsRepository.save(
            TeamEntity(
                id = UUID.randomUUID(),
                name = newTeamRequest.name.trim(),
                secret = newTeamRequest.secret,
                role = newTeamRequest.role
            )
        )
        return redirectToAdmin()
    }

    @Post("/admin/team/delete")
    @Secured("ADMIN")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    fun deleteTeam(auth: Authentication, @Body deleteTeamRequest: DeleteTeamRequest): HttpResponse<Any> {
        unlockRepository.deleteByTeamId(deleteTeamRequest.teamId)
        teamsRepository.deleteById(deleteTeamRequest.teamId)
        return redirectToAdmin()
    }

    object ViewController {
        fun redirectToAdmin(): HttpResponse<Any> = HttpResponse.seeOther(UriBuilder.of("/admin").build())
    }
}