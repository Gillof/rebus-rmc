package svampyrerna.repositories

import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import java.util.*

@JdbcRepository(dialect = Dialect.POSTGRES)
interface TeamsRepository : CrudRepository<TeamEntity, UUID> {
    fun findByName(name: String): TeamEntity?
}
