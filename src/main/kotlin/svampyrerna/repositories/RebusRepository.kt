package svampyrerna.repositories

import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.reactive.RxJavaCrudRepository
import java.util.*

@JdbcRepository(dialect = Dialect.POSTGRES)
interface RebusRepository : RxJavaCrudRepository<RebusEntity, UUID>
