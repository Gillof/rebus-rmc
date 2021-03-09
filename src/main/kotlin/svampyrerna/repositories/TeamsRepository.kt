package svampyrerna.repositories

import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.reactive.RxJavaCrudRepository
import io.reactivex.Maybe
import java.util.*

@JdbcRepository(dialect = Dialect.POSTGRES)
interface TeamsRepository : RxJavaCrudRepository<TeamEntity, UUID> {
    fun findByName(name: String): Maybe<TeamEntity>
}
