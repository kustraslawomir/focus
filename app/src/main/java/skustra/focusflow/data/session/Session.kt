package skustra.focusflow.data.session

import skustra.focusflow.data.alias.Minute
import skustra.focusflow.data.exceptions.SessionAlreadyCompletedException
import skustra.focusflow.data.timer.TimerState
import java.util.UUID

data class Session(
    var currentTimerState: TimerState = TimerState.Idle,
    var currentPartCounter: Int = 0,
    var duration: Minute,
    val parts: List<SessionPart>
) {
    fun currentSessionPart(): SessionPart {
        return parts[currentPartCounter]
    }

    @Throws(SessionAlreadyCompletedException::class)
    fun activateTheNextPartOfTheSession() {
        if (currentPartCounter < parts.size - 1) {
            currentPartCounter += 1
        } else throw SessionAlreadyCompletedException()
    }

    fun deepCopy(): Session {
        return Session(
            currentTimerState = currentTimerState,
            currentPartCounter = currentPartCounter,
            parts = parts,
            duration = duration
        )
    }

    fun sessionDuration(): Int {
        return duration
    }
}

sealed class SessionPartType {
    object Work : SessionPartType()
    object Break : SessionPartType()
}

data class SessionPart(
    val type: SessionPartType,
    val sessionPartDuration: Minute,
    val id: UUID = UUID.randomUUID()
)