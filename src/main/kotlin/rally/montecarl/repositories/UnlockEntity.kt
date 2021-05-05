package rally.montecarl.repositories

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

import java.util.*

@MappedEntity("unlocks")
data class UnlockEntity(
        @field:Id
        val id: UUID,
        val teamId: UUID,
        val rebusId: UUID,
        val type: UnlockType
)
