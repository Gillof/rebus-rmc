package svampyrerna.services

import svampyrerna.domain.RebusDTO
import svampyrerna.domain.TeamDTO
import svampyrerna.domain.TeamRebusDTO
import svampyrerna.repositories.*
import java.util.*
import javax.inject.Singleton

@Singleton
class RebusService(
        private val rebusRepo: RebusRepository,
        private val unlockRepo: UnlockRepository,
        private val teamsRepository: TeamsRepository,
) {

    fun rebuses(teamId: UUID): List<RebusDTO> {
        return unlockRepo.findByTeamId(teamId)
            .let { unlocks ->
                    rebusRepo.findAll()
                            .map { rebus ->
                                RebusDTO(
                                        rebus.id,
                                        rebus.nr,
                                        if (isUnlocked(rebus, unlocks, UnlockType.HINT)) rebus.hint else null,
                                        if (isUnlocked(rebus, unlocks, UnlockType.ANSWER)) rebus.answer else null,
                                )
                            }

                }
    }

    fun unlock(teamId: UUID, rebusId: UUID, unlockType: UnlockType): UnlockEntity {
        return unlockRepo.save(
                UnlockEntity(
                        UUID.randomUUID(),
                        teamId,
                        rebusId,
                        unlockType
                )
        )
    }

    private fun isUnlocked(rebus: RebusEntity, unlocks: List<UnlockEntity>, unlockType: UnlockType): Boolean {
        return unlocks
                .find { unlock -> unlock.type == unlockType && unlock.rebusId == rebus.id }
                ?.let { true } ?: false
    }

    fun getTeamOverview(): List<TeamRebusDTO> {
        return teamsRepository.findAll()
                .map { team ->
                    TeamRebusDTO(TeamDTO(team.id, team.name, team.role), rebuses(team.id))
                }
    }
}