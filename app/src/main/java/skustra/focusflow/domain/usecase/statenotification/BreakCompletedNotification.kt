package skustra.focusflow.domain.usecase.statenotification

import skustra.focusflow.R
import skustra.focusflow.domain.usecase.playsound.PlaySoundUseCase
import skustra.focusflow.domain.usecase.vibrate.SingleVibrationUseCase
import javax.inject.Inject

class BreakCompletedNotification @Inject constructor(
    private val vibrationUseCase: SingleVibrationUseCase,
    private val playSoundUseCase: PlaySoundUseCase
) : SessionPartCompletedNotification {

    override fun notifyUser() {
        vibrationUseCase.vibrate(milliseconds = 600)
        playSoundUseCase.play(R.raw.break_ends)
    }
}