package skustra.focusflow.ui.composables.session

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import skustra.focusflow.ui.composables.session.arc.SessionFocusArc
import skustra.focusflow.ui.composables.session.panel.SessionPanelComposable
import timber.log.Timber

@Composable
fun SessionComposable(viewModel: SessionViewModel = viewModel()) {

    val sessionState by viewModel
        .getSessionStateFlow()
        .collectAsStateWithLifecycle()

    Timber.d(
        "instance session composable: ${
            viewModel
                .getSessionStateFlow()
        }"
    )

    Timber.d("instance session composable sessionState: $sessionState")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
    ) {

        Box(contentAlignment = Alignment.Center) {
            SessionFocusArc(
                sessionState = sessionState
            )
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            SessionPanelComposable()
        }
    }
}
