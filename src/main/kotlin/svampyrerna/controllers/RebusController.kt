package svampyrerna.controllers

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.reactivex.Flowable
import svampyrerna.repositories.RebusEntity
import svampyrerna.repositories.RebusRepository

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/api")
class RebusController(
        private val rebusRepo: RebusRepository
) {

    @Get("rebus")
    fun getRebuses(auth: Authentication): Flowable<RebusEntity> {
        return rebusRepo.findAll()
    }

}
