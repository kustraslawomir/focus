package skustra.focusflow.data.model.session

import skustra.focusflow.data.database.entity.SessionCategoryEntity
import skustra.focusflow.data.model.alias.Minute
import skustra.focusflow.data.model.timer.TimerState
import java.util.UUID

data class Session(
    val sessionId: String = UUID.randomUUID().toString(),
    var currentTimerState: TimerState = TimerState.Idle,
    var currentPartCounter: Int = 0,
    var duration: Minute,
    val parts: List<SessionPart>,
    val category : SessionCategoryEntity = SessionCategoryEntity.Default
) {
    fun currentSessionPart(): SessionPart {
        return parts[currentPartCounter]
    }

    fun activateTheNextPartOfTheSession(
        onCurrentSessionPartIncremented: () -> Unit,
        onSessionCompleted: () -> Unit
    ) {
        if (currentPartCounter < parts.size - 1) {
            currentPartCounter += 1
            onCurrentSessionPartIncremented()
        } else {
            onSessionCompleted()
        }
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
