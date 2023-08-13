package com.sanmer.geomag.viewmodel

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.sanmer.geomag.GeomagExt
import com.sanmer.geomag.model.Position
import com.sanmer.geomag.model.Record
import com.sanmer.geomag.repository.LocalRepository
import com.sanmer.geomag.repository.UserDataRepository
import com.sanmer.geomag.service.CalculateService
import com.sanmer.geomag.service.LocationService
import com.sanmer.geomag.utils.extensions.now
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val userDataRepository: UserDataRepository
): ViewModel() {
    var isTimeRunning by mutableStateOf(true)
        private set
    private val dateTimeFlow: Flow<DateTime> = flow {
        while (currentCoroutineContext().isActive) {
            if (isTimeRunning) {
                emit(DateTime.now())
                delay(1000)
            }
        }
    }.flowOn(Dispatchers.Default)

    val isLocationRunning get() = LocationService.isRunning
    val position by derivedStateOf {
        Position(LocationService.location)
    }

    val isCalculateRunning get() = CalculateService.isRunning
    private var _currentValue by mutableStateOf(Record.empty())
    val currentValue get() = if (isCalculateRunning) {
        CalculateService.currentValue
    } else {
        _currentValue
    }

    val userData get() = userDataRepository.userData
    fun setFieldModel(value: GeomagExt.Models) = userDataRepository.setFieldModel(value)
    fun setEnableRecords(value: Boolean) = userDataRepository.setEnableRecords(value)

    init {
        Timber.d("HomeViewModel init")
    }

    fun toggleDateTime() {
        isTimeRunning = !isTimeRunning
    }

    fun toggleLocation(context: Context) {
        if (isLocationRunning) {
            LocationService.stop(context)
        } else {
            LocationService.start(context)
        }
    }

    fun toggleCalculate(context: Context) {
        if (isCalculateRunning) {
            CalculateService.stop(context)
        } else {
            CalculateService.start(context)
        }
    }

    fun singleCalculate(
        dateTime: LocalDateTime,
        position: Position,
        onFinished: () -> Unit
    ) = viewModelScope.launch {
        val model = userDataRepository.value.fieldModel
        val enableRecords = userDataRepository.value.enableRecords

        _currentValue = GeomagExt.run(
            model = model,
            dataTime = dateTime,
            position = position
        )

        if (enableRecords) {
            localRepository.insert(_currentValue)
        }

        onFinished()
    }

    @Stable
    data class DateTime(
        val value: LocalDateTime,
        val decimal: Double
    ) {
        companion object {
            fun now(): DateTime {
                val value = LocalDateTime.now()
                val decimal = GeomagExt.toDecimalYears(value)

                return DateTime(value, decimal)
            }
        }
    }

    @Composable
    fun rememberDateTime(): DateTime {
        val dataTime = dateTimeFlow.collectAsStateWithLifecycle(
            initialValue = DateTime.now()
        )

        return dataTime.value
    }

    @Composable
    fun UpdateCalculateParameters(
        dateTime: LocalDateTime,
        position: Position
    ) = LaunchedEffect(key1 = dateTime, key2 = position) {
        if (isCalculateRunning) {
            CalculateService.updateParameters(
                dateTime = dateTime,
                position = position
            )
        }
    }
}