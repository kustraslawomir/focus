package skustra.focusflow.ui.composables.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import skustra.focusflow.BuildConfig
import skustra.focusflow.data.database.entity.SessionArchiveEntity
import skustra.focusflow.data.repository.SessionArchiveRepository
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.domain.utilities.dates.StatisticDateUtils
import skustra.focusflow.domain.utilities.dates.StatisticDateUtils.generateDates
import timber.log.Timber
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val sessionArchiveRepository: SessionArchiveRepository
) : ViewModel() {

    private val entryProducer = ChartEntryModelProducer()

    fun getEntryProducer(): ChartEntryModelProducer {
        return entryProducer
    }

    init {
        createEmptyStatistics()
        listenToStatisticsChange()
    }

    private fun createEmptyStatistics() {
        viewModelScope.launch {
            if (!sessionArchiveRepository.isEmpty()) {
                return@launch
            }

            val durations = SessionConfig.availableDurations()
            val dates = generateDates().map { dayInterval ->
                val randomDuration = durations[Random.nextInt(0, durations.size - 1)]
                SessionArchiveEntity(
                    formattedDate = StatisticDateUtils.format(dayInterval),
                    sessionId = UUID.randomUUID().toString(),
               //     minutes = if (BuildConfig.DEBUG) randomDuration else 0,
                    minutes = 0,
                    dateMs = dayInterval.time
                )
            }
            sessionArchiveRepository.insert(dates)
        }
    }

    private fun listenToStatisticsChange() {
        viewModelScope.launch {
            sessionArchiveRepository.getAllAsFlow().collect { data ->
                Timber.d("Statistics size: ${data.size}")
                val producer = data
                    .groupBy { it.formattedDate }
                    .map { item ->
                        val durationSum = item.value.sumOf { it.minutes }
                        SessionArchiveEntry(
                            sessionArchiveEntryDataModel = SessionArchiveEntryDataModel(
                                summedDayDuration = durationSum,
                                date = item.key
                            ),
                            y = durationSum.toFloat(),
                            x = data.indexOf(item.value.first()).toFloat(),
                        )
                    }

                entryProducer.setEntries(producer)
            }
        }
    }
}