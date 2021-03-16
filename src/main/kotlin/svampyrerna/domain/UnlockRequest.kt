package svampyrerna.domain

import io.micronaut.core.annotation.Introspected
import svampyrerna.repositories.UnlockType
import java.util.*

@Introspected
class UnlockRequest(
        val rebusId: UUID,
        val unlockType: UnlockType
)
