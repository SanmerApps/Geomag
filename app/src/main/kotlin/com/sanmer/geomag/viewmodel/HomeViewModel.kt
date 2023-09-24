package com.sanmer.geomag.viewmodel

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sanmer.geomag.GeomagExt
import com.sanmer.geomag.model.Position
import com.sanmer.geomag.model.Record
import com.sanmer.geomag.repository.LocalRepository
import com.sanmer.geomag.repository.UserPreferencesRepository
import com.sanmer.geomag.service.LocationService
import com.sanmer.geomag.utils.extensions.now
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
    private val userPreferences get() = userPreferencesRepository.data

    var isTimeRunning by mutableStateOf(true)
        private set

    private val dateTimeFlow = flow {
        while (currentCoroutineContext().isActive) {
            if (isTimeRunning) {
                emit(LocalDateTime.now().withDecimalYears())
                delay(1000)
            }
        }
    }.flowOn(Dispatchers.Default)

    var isCalculateRunning by mutableStateOf(false)
        private set

    private val currentValueFlow = flow {
        while (currentCoroutineContext().isActive) {
            if (isCalculateRunning) {
                singleCalculate().onSuccess { emit(it) }
                delay(1000)
            }
        }
    }.flowOn(Dispatchers.Default)

    val isLocationRunning get() = LocationService.isRunning
    val position by derivedStateOf {
        Position(LocationService.location)
    }

    init {
        Timber.d("HomeViewModel init")
    }

    private fun LocalDateTime.withDecimalYears(): Pair<LocalDateTime, Double> =
        this to GeomagExt.toDecimalYears(this)

    fun toggleDateTime() {
        isTimeRunning = !isTimeRunning
    }

    fun toggleCalculate() {
        isCalculateRunning = !isCalculateRunning
    }

    fun toggleLocation(context: Context) {
        if (isLocationRunning) {
            LocationService.stop(context)
        } else {
            LocationService.start(context)
        }
    }

    suspend fun singleCalculate(): Result<Record> = withContext(Dispatchers.IO) {
        runCatching {
            val preference = userPreferences.first()
            val model = preference.fieldModel
            val enableRecords = preference.enableRecords
            val dateTime = dateTimeFlow.first().first

            val value = GeomagExt.run(
                model = model,
                dataTime = dateTime,
                position = position
            )

            if (enableRecords) {
                localRepository.insert(value)
            }

            return@runCatching value
        }.onFailure {
            Timber.e(it)
        }
    }

    @Composable
    fun rememberDateTime(): Pair<LocalDateTime, Double> {
        val dataTime = dateTimeFlow.collectAsStateWithLifecycle(
            initialValue = LocalDateTime.now().withDecimalYears()
        )

        return dataTime.value
    }

    @Composable
    fun rememberCurrentValue(): Record {
        val currentValue = currentValueFlow.collectAsStateWithLifecycle(
            initialValue = Record.empty()
        )

        return currentValue.value
    }

    fun setFieldModel(value: GeomagExt.Models) =
        userPreferencesRepository.setFieldModel(value)
    fun setEnableRecords(value: Boolean) =
        userPreferencesRepository.setEnableRecords(value)
}