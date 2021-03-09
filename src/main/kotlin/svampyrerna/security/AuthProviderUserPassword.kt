package svampyrerna.security

import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.UserDetails
import org.reactivestreams.Publisher
import svampyrerna.repositories.TeamsRepository
import javax.annotation.Nullable
import javax.inject.Singleton


@Singleton
class AuthenticationProviderUserPassword(private val usersRepo: TeamsRepository) : AuthenticationProvider {
    override fun authenticate(
            @Nullable httpRequest: HttpRequest<*>?,
            authenticationRequest: AuthenticationRequest<*, *>
    ): Publisher<AuthenticationResponse> {
        return usersRepo.findByName(authenticationRequest.identity as String)
                .filter { it.secret == authenticationRequest.secret }
                .map { _ -> UserDetails(authenticationRequest.identity as String, mutableListOf()) as AuthenticationResponse }
                .toFlowable()
    }
}
