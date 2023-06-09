package skustra.focusflow.ui.features.session.description

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import skustra.focusflow.data.model.session.Session
import skustra.focusflow.data.model.session.SessionPart
import skustra.focusflow.data.model.session.SessionPartType
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager

@Composable
fun SessionDescriptionComposable(session: Session) {
    when (session.currentTimerState) {
        TimerState.Idle -> IdleDescription()
        is TimerState.InProgress -> InProgressDescription(session.currentSessionPart())
        is TimerState.Paused -> PauseDescription()
        is TimerState.Completed -> {
            //ignore
        }
    }
}

@Composable
fun IdleDescription() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Title(LocalizationManager.getText(LocalizationKey.SessionIdleTitle))
        Description(LocalizationManager.getText(LocalizationKey.SessionIdleDescription))
    }
}

@Composable
fun InProgressDescription(sessionState: SessionPart) {
    when (sessionState.type) {
        SessionPartType.Break -> Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Title(LocalizationManager.getText(LocalizationKey.SessionBreakTitle))
            Description(LocalizationManager.getText(LocalizationKey.SessionBreakDescription))
        }

        SessionPartType.Work -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Title(LocalizationManager.getText(LocalizationKey.SessionInProgressTitle))
                Description(LocalizationManager.getText(LocalizationKey.SessionInProgressDescription))
            }
        }
    }
}

@Composable
fun PauseDescription() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Title(LocalizationManager.getText(LocalizationKey.SessionPauseTitle))
        Description(LocalizationManager.getText(LocalizationKey.SessionPauseDescription))
    }
}

@Composable
private fun Description(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.bodyMedium,
        color = Color.White,
        modifier = Modifier.alpha(0.5f),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun Title(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleLarge,
        color = Color.White
    )
}
