package svampyrerna.domain

import svampyrerna.repositories.TeamRole
import java.util.*

data class TeamDTO(
        val id: UUID,
        val name: String,
        val role: TeamRole? = null
)
