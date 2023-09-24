package com.sanmer.geomag.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanmer.geomag.database.entity.RecordKey
import com.sanmer.geomag.model.Record
import com.sanmer.geomag.repository.LocalRepository
import com.sanmer.geomag.ui.navigation.graphs.RecordsScreen
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
    private val recordKey = getRecordKey(savedStateHandle)
    var record: Record = Record.empty()
        private set

    init {
        Timber.d("RecordViewModel init")

        viewModelScope.launch {
            runCatching {
                localRepository.getByKey(key = recordKey)
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

    companion object {
        fun putRecordKey(record: Record) =
            RecordsScreen.View.route.replace(
                "{recordKey}", Uri.encode(RecordKey(record).toString())
            )

        fun getRecordKey(savedStateHandle: SavedStateHandle) =
            RecordKey.parse(
                Uri.decode(savedStateHandle["recordKey"])
            )
    }
}