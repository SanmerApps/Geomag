package com.sanmer.geomag.ui.activity.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sanmer.geomag.datastore.UserData
import com.sanmer.geomag.datastore.isDarkMode
import com.sanmer.geomag.repository.UserDataRepository
import com.sanmer.geomag.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : ComponentActivity() {
    @Inject
    lateinit var userDataRepository: UserDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }

    fun setActivityContent(
        content: @Composable (UserData) -> Unit
    ) = setContent {
        val userData by userDataRepository.userData.collectAsStateWithLifecycle(UserData.default())

        AppTheme(
            darkMode = userData.isDarkMode(),
            themeColor = userData.themeColor
        ) {
            content(userData)
        }
    }
}