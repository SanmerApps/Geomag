package com.sanmer.geomag.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanmer.geomag.model.origin.Record
import com.sanmer.geomag.repository.LocalRepository
import com.sanmer.geomag.utils.JsonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {
    private val valuesFlow = MutableStateFlow(listOf<Record>())
    val records get() = valuesFlow.asStateFlow()

    private val selected = mutableStateListOf<Record>()
    val selectedSize get() = selected.size

    var isChooser by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
        private set

    init {
        Timber.d("RecordsViewModel init")
        dataObserver()
    }

    private fun dataObserver() {
        localRepository.getAllAsFlow()
            .onStart { isLoading = true }
            .onEach { list ->
                valuesFlow.value = list.sortedBy { it.time.local }

                if (isLoading) isLoading = false

            }.launchIn(viewModelScope)
    }

    fun isSelected(value: Record) = value in selected

    fun closeChooser() {
        isChooser = false
        selected.clear()
    }

    fun toggleRecord(value: Record) {
        if (isSelected(value)) {
            selected.remove(value)
        } else {
            selected.add(value)
        }
    }

    fun shareJsonFile(context: Context) {
        JsonUtils.shareJsonFile(context, selected)
        closeChooser()
    }

    fun deleteSelected() = viewModelScope.launch {
        localRepository.delete(selected)
        closeChooser()
    }
}