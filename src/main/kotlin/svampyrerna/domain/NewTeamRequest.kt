package svampyrerna.domain

import svampyrerna.repositories.TeamRole

data class NewTeamRequest(
    val name: String,
    val secret: String,
    val role: TeamRole?
)
