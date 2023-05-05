package skustra.focusflow.domain.usecase.timer

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import skustra.focusflow.data.model.alias.Minute
import skustra.focusflow.data.model.timer.Progress
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.domain.usecase.interval.launchPeriodicAsync

class SessionStateEmitterImpl : SessionStateEmitter {

    private val mutableTimerState = MutableStateFlow<TimerState>(TimerState.Idle)
    private val timerState: StateFlow<TimerState> = mutableTimerState

    private var duration: Minute = 0
    private var currentProgress: Minute = 0
    private var isPaused = false
    private var intervalJob: Deferred<Unit>? = null
    private lateinit var scope: CoroutineScope

    override fun start(sessionDuration: Minute, scope: CoroutineScope) {

        this.duration = sessionDuration
        this.currentProgress = sessionDuration
        this.scope = scope

        scope.launch {
            mutableTimerState.emit(
                TimerState.InProgress(
                    progress = Progress(
                        sessionDuration = sessionDuration,
                        minutesLeft = currentProgress
                    )
                )
            )
        }
        cancelInterval()
        runInterval(scope)
    }

    private fun runInterval(scope: CoroutineScope) {
        intervalJob = scope.launchPeriodicAsync {
            scope.launch {
                if (!isActive) {
                    return@launch
                }

                if (isPaused) {
                    return@launch
                }

                currentProgress -= 1
                if (sessionEnded()) {
                    cancelInterval()
                    mutableTimerState.emit(TimerState.Completed)
                    return@launch
                }

                mutableTimerState.emit(
                    TimerState.InProgress(
                        progress = getCurrentSessionProgress()
                    )
                )
            }
        }
    }

    override fun pause() {
        isPaused = true
        scope.launch {
            mutableTimerState.emit(
                TimerState.Paused(
                    progress = getCurrentSessionProgress()
                )
            )
        }
    }

    override fun resume() {
        isPaused = false
        scope.launch {
            mutableTimerState.emit(
                TimerState.InProgress(
                    progress = getCurrentSessionProgress()
                )
            )
        }
    }

    override fun stop() {
        cancelInterval()
        isPaused = false
        scope.launch {
            mutableTimerState.emit(TimerState.Idle)
        }
    }

    private fun getCurrentSessionProgress(): Progress {
        return Progress(
            minutesLeft = currentProgress,
            sessionDuration = duration
        )
    }

    override fun getCurrentState(): StateFlow<TimerState> {
        return timerState
    }

    private fun sessionEnded(): Boolean {
        return currentProgress <= 0
    }

    private fun cancelInterval() {
        intervalJob?.cancel()
    }
}