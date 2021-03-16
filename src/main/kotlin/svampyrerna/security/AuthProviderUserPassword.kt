package svampyrerna.security

import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.UserDetails
import org.reactivestreams.Publisher
import svampyrerna.repositories.TeamsRepository
import javax.annotation.Nullable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthProviderUserPassword @Inject constructor(private val usersRepo: TeamsRepository) : AuthenticationProvider {
    override fun authenticate(
            @Nullable httpRequest: HttpRequest<*>?,
            authenticationRequest: AuthenticationRequest<*, *>
    ): Publisher<AuthenticationResponse> {
        return usersRepo.findByName(authenticationRequest.identity as String)
                .filter { it.secret == authenticationRequest.secret }
                .map { team ->
                    UserDetails(
                            team.name,
                            mutableListOf(team.role).filterNotNull().map { it.name },
                            hashMapOf("teamId" to team.id.toString()) as Map<String, Any>?
                    ) as AuthenticationResponse
                }
                .toFlowable()
    }
}
