package svampyrerna.security

import com.nimbusds.jose.shaded.json.JSONArray
import io.micronaut.security.authentication.Authentication
import svampyrerna.repositories.TeamRole
import java.util.*

object AuthHelper {
    fun getTeamId(auth: Authentication): UUID {
        return UUID.fromString((auth.attributes["teamId"] ?: error("Invalid token: teamId not found")) as String)
    }

    fun getAdminRole(auth: Authentication): TeamRole? {
        return auth.attributes["roles"]
            ?.let { it as JSONArray }
            ?.let { it.map { r -> TeamRole.valueOf(r as String) } }
            ?.let { it.find { r -> TeamRole.ADMIN == r }}
    }
}