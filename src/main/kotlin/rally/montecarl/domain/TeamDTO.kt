package rally.montecarl.domain

import rally.montecarl.repositories.TeamRole
import java.util.*

data class TeamDTO(
        val id: UUID,
        val name: String,
        val role: TeamRole? = null
)
