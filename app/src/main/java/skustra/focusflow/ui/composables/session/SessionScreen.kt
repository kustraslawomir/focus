package skustra.focusflow.ui.composables.session

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import skustra.focusflow.ui.composables.session.arc.SessionFocusArc
import skustra.focusflow.ui.composables.session.description.SessionDescriptionComposable
import skustra.focusflow.ui.composables.session.panel.SessionPanelComposable

@Composable
fun SessionScreen(viewModel: SessionViewModel = viewModel()) {

    val session by viewModel
        .getSessionFlow()
        .collectAsStateWithLifecycle()

    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter

        ) {
            SessionDescriptionComposable(
                session = session
            )
        }
        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            SessionFocusArc(
                session = session,
                decreaseSessionDuration = viewModel::decreaseSessionDuration,
                increaseSessionDuration = viewModel::increaseSessionDuration,
                isAvailableToDecrease = viewModel.isAvailableToDecrease(),
                isAvailableToIncrease = viewModel.isAvailableToIncrease()
            )
        }
        Box(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            SessionPanelComposable(
                session = session,
                startSession = {
                    viewModel.onSessionUiEvent(SessionUiEvent.Start(context))
                },
                pauseSession = { viewModel.onSessionUiEvent(SessionUiEvent.Pause) },
                resumeSession = { viewModel.onSessionUiEvent(SessionUiEvent.Resume) },
                stopSession = { viewModel.onSessionUiEvent(SessionUiEvent.Stop) },
                sessionIncludesBreaks = viewModel.sessionIncludesBreaks(),
                skipBreaks = viewModel.skipTheBreaks(),
                drawableProvider = viewModel.drawableProvider,
                shouldSkipBreaks = { skip ->
                    viewModel.onSessionUiEvent(
                        SessionUiEvent.SkipBreaks(
                            skip
                        )
                    )
                }
            )
        }
    }
}
