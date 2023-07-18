package com.sanmer.geomag.repository

import com.sanmer.geomag.GeomagExt
import com.sanmer.geomag.datastore.DarkMode
import com.sanmer.geomag.datastore.UserData
import com.sanmer.geomag.datastore.UserPreferencesDataSource
import com.sanmer.geomag.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataRepository @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource,
    @ApplicationScope private val applicationScope: CoroutineScope
) {
    val userData get() = userPreferencesDataSource.userData

    private var _value = UserData.default()
    val value get() = _value

    init {
        userPreferencesDataSource.userData
            .distinctUntilChanged()
            .onEach {
                _value = it
            }.launchIn(applicationScope)
    }

    fun setDarkTheme(value: DarkMode) = applicationScope.launch {
        userPreferencesDataSource.setDarkTheme(value)
    }

    fun setThemeColor(value: Int) = applicationScope.launch {
        userPreferencesDataSource.setThemeColor(value)
    }

    fun setFieldModel(value: GeomagExt.Models) = applicationScope.launch {
        userPreferencesDataSource.setFieldModel(value.name)
    }

    fun setEnableRecords(value: Boolean) = applicationScope.launch {
        userPreferencesDataSource.setEnableRecords(value)
    }
}