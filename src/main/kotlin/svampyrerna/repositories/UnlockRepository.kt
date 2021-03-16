package svampyrerna.repositories

import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.reactive.RxJavaCrudRepository
import io.reactivex.Flowable
import java.util.*

@JdbcRepository(dialect = Dialect.POSTGRES)
interface UnlockRepository : RxJavaCrudRepository<UnlockEntity, UUID> {
    fun findByTeamId(teamId: UUID): Flowable<UnlockEntity>
}
