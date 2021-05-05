package rally.montecarl.domain

import rally.montecarl.repositories.TeamRole

data class NewTeamRequest(
    val name: String,
    val secret: String,
    val role: TeamRole?
)
