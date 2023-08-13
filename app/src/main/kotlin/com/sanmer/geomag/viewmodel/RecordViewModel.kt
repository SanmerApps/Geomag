package com.sanmer.geomag.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanmer.geomag.model.Record
import com.sanmer.geomag.repository.LocalRepository
import com.sanmer.geomag.utils.JsonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val primaryKey: String = checkNotNull(savedStateHandle["primaryKey"])
    var record: Record = Record.empty()
        private set

    init {
        Timber.d("RecordViewModel init: $primaryKey")

        viewModelScope.launch {
            runCatching {
                localRepository.getById(primaryKey.toDouble())
            }.onSuccess {
                record = it
            }
        }
    }

    fun share(context: Context) {
        JsonUtils.shareJsonFile(context, record)
    }

    fun delete() = viewModelScope.launch {
        localRepository.delete(record)
    }
}