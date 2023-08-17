package com.sanmer.geomag.ui.activity.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sanmer.geomag.datastore.UserPreferencesExt
import com.sanmer.geomag.datastore.isDarkMode
import com.sanmer.geomag.repository.UserPreferencesRepository
import com.sanmer.geomag.ui.providable.LocalUserPreferences
import com.sanmer.geomag.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : ComponentActivity() {
    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }

    fun setActivityContent(
        content: @Composable () -> Unit
    ) = setContent {
        val userPreferences by userPreferencesRepository.data
            .collectAsStateWithLifecycle(UserPreferencesExt.default())

        CompositionLocalProvider(
            LocalUserPreferences provides userPreferences
        ) {
            AppTheme(
                darkMode = userPreferences.isDarkMode(),
                themeColor = userPreferences.themeColor,
                content = content
            )
        }
    }
}