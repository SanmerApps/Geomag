package com.sanmer.geomag.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    val userData get() = userPreferences.data.map { it.toUserData() }

    suspend fun setDarkTheme(value: DarkMode) = withContext(Dispatchers.IO) {
        userPreferences.updateData {
            it.copy {
                darkMode = value
            }
        }
    }

    suspend fun setThemeColor(value: Int) = withContext(Dispatchers.IO) {
        userPreferences.updateData {
            it.copy {
                themeColor = value
            }
        }
    }

    suspend fun setFieldModel(value: String) = withContext(Dispatchers.IO) {
        userPreferences.updateData {
            it.copy {
                fieldModel = value
            }
        }
    }

    suspend fun setEnableRecords(value: Boolean) = withContext(Dispatchers.IO) {
        userPreferences.updateData {
            it.copy {
                enableRecords = value
            }
        }
    }
}