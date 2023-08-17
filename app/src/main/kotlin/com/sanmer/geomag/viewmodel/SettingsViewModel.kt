package com.sanmer.geomag.viewmodel

import androidx.lifecycle.ViewModel
import com.sanmer.geomag.datastore.DarkMode
import com.sanmer.geomag.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    init {
        Timber.d("SettingsViewModel init")
    }

    fun setDarkTheme(value: DarkMode) =
        userPreferencesRepository.setDarkTheme(value)

    fun setThemeColor(value: Int) =
        userPreferencesRepository.setThemeColor(value)
}