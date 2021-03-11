package svampyrerna.repositories

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import java.util.*

@MappedEntity("teams")
data class TeamEntity(
        @field:Id
        val id: UUID,
        val name: String,
        val secret: String,
        val role: TeamRole?
)
