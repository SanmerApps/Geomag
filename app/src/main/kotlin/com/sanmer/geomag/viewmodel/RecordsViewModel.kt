package com.sanmer.geomag.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanmer.geomag.model.Record
import com.sanmer.geomag.repository.LocalRepository
import com.sanmer.geomag.utils.JsonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {
    val list = localRepository.getAllAsFlow()
        .map { list ->
            list.sortedBy { it.time }
                .toMutableStateList()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val selectedList = mutableStateListOf<Record>()
    val selectedSize get() = selectedList.size

    var isChooser by mutableStateOf(false)
        private set

    init {
        Timber.d("RecordsViewModel init")
    }

    fun isSelected(value: Record) = value in selectedList

    fun enableChooser(value: Boolean) {
        isChooser = value
        selectedList.clear()
    }

    fun toggleRecord(value: Record) {
        if (isSelected(value)) {
            selectedList.remove(value)
        } else {
            selectedList.add(value)
        }
    }

    fun shareJsonFile(context: Context) {
        JsonUtils.shareJsonFile(context, selectedList)
        enableChooser(false)
    }

    fun deleteSelected() = viewModelScope.launch {
        localRepository.delete(selectedList)
        enableChooser(false)
    }
}