package com.sanmer.geomag.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanmer.geomag.datastore.DarkMode
import com.sanmer.geomag.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    init {
        Timber.d("SettingsViewModel init")
    }

    fun setDarkTheme(value: DarkMode) {
        viewModelScope.launch {
            userPreferencesRepository.setDarkTheme(value)
        }
    }

    fun setThemeColor(value: Int) {
        viewModelScope.launch {
            userPreferencesRepository.setThemeColor(value)
        }
    }
}