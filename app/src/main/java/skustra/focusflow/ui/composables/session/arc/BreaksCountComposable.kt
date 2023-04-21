package skustra.focusflow.ui.composables.session.arc
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import skustra.focusflow.data.session.Session
import skustra.focusflow.data.session.SessionPartType
import skustra.focusflow.data.timer.TimerState
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager

@Composable
fun BreaksCount(sessionState: Session) {
    if (sessionState.currentTimerState != TimerState.Idle) {
        return
    }

    val breaksCount = sessionState.parts.count { sessionPart ->
        sessionPart.type == SessionPartType.Break
    }

    val breaksCountText = when (breaksCount) {
        0 -> LocalizationManager.getText(LocalizationKey.NoBreakIncluded)
        1 -> LocalizationManager.getText(LocalizationKey.SingleBreak)
        else -> "${LocalizationManager.getText(LocalizationKey.BreakCount)} $breaksCount"
    }

    Text(
        text = breaksCountText, style = MaterialTheme.typography.bodySmall
    )
}