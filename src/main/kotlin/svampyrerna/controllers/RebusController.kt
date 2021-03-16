package svampyrerna.controllers

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.reactivex.Completable
import io.reactivex.Single
import svampyrerna.domain.AdminOverview
import svampyrerna.domain.TeamDTO
import svampyrerna.domain.TeamRebusDTO
import svampyrerna.domain.UnlockRequest
import svampyrerna.security.AuthHelper
import svampyrerna.services.RebusService

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
                .map { TeamRebusDTO(TeamDTO(teamId, auth.name), it) }
    }

    @Post("unlock")
    fun getRebuses(auth: Authentication, @Body unlockReq: UnlockRequest): Completable {
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
