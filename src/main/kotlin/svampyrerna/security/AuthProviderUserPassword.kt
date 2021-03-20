package svampyrerna.security

import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
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
        return Flowable.create({ emitter ->
            usersRepo.findByName(authenticationRequest.identity as String)
                ?.let { team -> if (team.secret == authenticationRequest.secret) team else null }
                ?.let { team ->
                    UserDetails(
                        team.name,
                        mutableListOf(team.role).filterNotNull().map { it.name },
                        hashMapOf("teamId" to team.id.toString()) as Map<String, Any>?
                    )
                }
                ?.also { user -> emitter.onNext(user); emitter.onComplete(); }
                ?: emitter.onError(AuthenticationException(AuthenticationFailed()))
        }, BackpressureStrategy.ERROR)
    }
}
