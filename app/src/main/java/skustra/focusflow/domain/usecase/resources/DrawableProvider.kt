package skustra.focusflow.domain.usecase.resources

interface DrawableProvider {

    fun getPauseIcon(): Int

    fun getResumeIcon(): Int

    fun getPlayIcon(): Int

    fun getStopIcon(): Int

    fun getStatisticsIcon(): Int

    fun getChevronUpIcon(): Int

    fun getNotificationIcon(): Int
}
