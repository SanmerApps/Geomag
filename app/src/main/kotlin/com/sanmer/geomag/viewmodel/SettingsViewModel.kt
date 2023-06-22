package com.sanmer.geomag.viewmodel

import androidx.lifecycle.ViewModel
import com.sanmer.geomag.datastore.DarkMode
import com.sanmer.geomag.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : ViewModel() {
    val userData get() = userDataRepository.userData

    init {
        Timber.d("SettingsViewModel init")
    }

    fun setDarkTheme(value: DarkMode) = userDataRepository.setDarkTheme(value)
    fun setThemeColor(value: Int) = userDataRepository.setThemeColor(value)
}