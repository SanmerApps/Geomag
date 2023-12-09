package com.sanmer.geomag.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sanmer.geomag.app.utils.LocationManagerUtils
import com.sanmer.geomag.app.utils.NotificationUtils
import com.sanmer.geomag.app.utils.OsUtils
import com.sanmer.geomag.datastore.isDarkMode
import com.sanmer.geomag.repository.UserPreferencesRepository
import com.sanmer.geomag.ui.providable.LocalUserPreferences
import com.sanmer.geomag.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    private var isLoading by mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        splashScreen.setKeepOnScreenCondition { isLoading }

        setContent {
            LocationManagerUtils.PermissionsState()

            if (OsUtils.atLeastT) {
                NotificationUtils.PermissionState()
            }

            val userPreferences by userPreferencesRepository.data
                .collectAsStateWithLifecycle(initialValue = null)

            if (userPreferences == null) {
                // Keep on splash screen
                return@setContent
            } else {
                isLoading = false
            }

            CompositionLocalProvider(
                LocalUserPreferences provides userPreferences!!
            ) {
                AppTheme(
                    darkMode = userPreferences!!.isDarkMode(),
                    themeColor = userPreferences!!.themeColor
                ) {
                    MainScreen()
                }
            }
        }
    }
}