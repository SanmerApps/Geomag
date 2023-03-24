package com.sanmer.geomag.ui.activity.log

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.WindowCompat
import com.sanmer.geomag.service.LogcatService
import com.sanmer.geomag.ui.theme.AppTheme

class LogActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            LaunchedEffect(LogcatService.isActive) {
                if (!LogcatService.isActive) {
                    LogcatService.start(this@LogActivity)
                }
            }

            AppTheme {
                LogScreen()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LogcatService.stop(this)
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LogActivity::class.java)
            context.startActivity(intent)
        }
    }
}