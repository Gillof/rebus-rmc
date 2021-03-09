package svampyrerna.repositories

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import java.util.*

@MappedEntity("rebus")
data class RebusEntity(
        @field:Id
        val id: UUID,
        val nr: Int,
        val hint: String,
        val answer: String
)
