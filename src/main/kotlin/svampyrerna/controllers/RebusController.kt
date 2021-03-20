package svampyrerna.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.tags.Tag
import svampyrerna.domain.AdminOverview
import svampyrerna.domain.TeamDTO
import svampyrerna.domain.TeamRebusDTO
import svampyrerna.domain.UnlockRequest
import svampyrerna.security.AuthHelper
import svampyrerna.services.RebusService

@Tag(name = "API")
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/api")
class RebusController(
        private val rebusService: RebusService
) {

    @Get("rebus")
    fun getRebuses(auth: Authentication): TeamRebusDTO {
        val teamId = AuthHelper.getTeamId(auth)
        return TeamRebusDTO(TeamDTO(teamId, auth.name, AuthHelper.getAdminRole(auth)), rebusService.rebuses(teamId))
    }

    @Consumes(MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED)
    @Post("unlock")
    fun unlock(auth: Authentication, @Body unlockReq: UnlockRequest): HttpResponse<*> {
        val teamId = AuthHelper.getTeamId(auth)
        rebusService.unlock(teamId, unlockReq.rebusId, unlockReq.unlockType)
        return HttpResponse.noContent<Any>()
    }

    @Get("admin")
    @Secured("ADMIN")
    fun getAdminOverview(): AdminOverview {
        return AdminOverview(rebusService.getTeamOverview())
    }

}
