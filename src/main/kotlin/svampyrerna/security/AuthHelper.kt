package svampyrerna.security

import io.micronaut.security.authentication.Authentication
import java.util.*

object AuthHelper {
    fun getTeamId(auth: Authentication): UUID {
        return UUID.fromString((auth.attributes["teamId"] ?: error("Invalid token: teamId not found")) as String)
    }
}