package rally.montecarl.domain

import java.util.*

data class RebusDTO(
    val id: UUID,
    val nr: Int,
    val hint: String?,
    val answer: String?
)