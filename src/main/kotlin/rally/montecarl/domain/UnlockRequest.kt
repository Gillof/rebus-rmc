package rally.montecarl.domain

import io.micronaut.core.annotation.Introspected
import rally.montecarl.repositories.UnlockType
import java.util.*

@Introspected
class UnlockRequest(
        val rebusId: UUID,
        val unlockType: UnlockType
)
