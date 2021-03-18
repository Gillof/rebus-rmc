package svampyrerna.controllers

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.reactivex.Completable
import io.reactivex.Single
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
    fun getRebuses(auth: Authentication): Single<TeamRebusDTO> {
        val teamId = AuthHelper.getTeamId(auth)
        return rebusService.rebuses(teamId)
                .toList()
                .map { TeamRebusDTO(TeamDTO(teamId, auth.name, AuthHelper.getAdminRole(auth)), it) }
    }

    @Consumes(MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED)
    @Post("unlock")
    fun unlock(auth: Authentication, @Body unlockReq: UnlockRequest): Completable {
        val teamId = AuthHelper.getTeamId(auth)
        return rebusService.unlock(teamId, unlockReq.rebusId, unlockReq.unlockType)
                .ignoreElement()
    }

    @Get("admin")
    @Secured("ADMIN")
    fun getAdminOverview(): Single<AdminOverview> {
        return rebusService.getTeamOverview()
                .toList()
                .map { AdminOverview(it) }
    }

}
