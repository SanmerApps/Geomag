package com.sanmer.geomag.ui.activity.log

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.sanmer.geomag.service.LogcatService
import com.sanmer.geomag.ui.theme.AppTheme

class LogActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                val context = this
                LaunchedEffect(LogcatService.isActive) {
                    if (!LogcatService.isActive) {
                        LogcatService.start(context)
                    }
                }

                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LogScreen()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LogcatService.stop(this)
    }
}