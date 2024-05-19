package com.sanmer.geomag.repository

import com.sanmer.geomag.Compat
import com.sanmer.geomag.datastore.DarkMode
import com.sanmer.geomag.datastore.UserPreferencesDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepository @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource
) {
    val data get() = userPreferencesDataSource.data

    suspend fun setDarkTheme(value: DarkMode) = userPreferencesDataSource.setDarkTheme(value)

    suspend fun setThemeColor(value: Int) = userPreferencesDataSource.setThemeColor(value)

    suspend fun setFieldModel(value: Compat.Models) = userPreferencesDataSource.setFieldModel(value.name)

    suspend fun setEnableRecords(value: Boolean) = userPreferencesDataSource.setEnableRecords(value)
}