package com.sanmer.geomag.ui.activity.main

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sanmer.geomag.app.utils.LocationManagerUtils
import com.sanmer.geomag.app.utils.NotificationUtils
import com.sanmer.geomag.app.utils.OsUtils
import com.sanmer.geomag.ui.activity.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        setActivityContent { isReady ->
            splashScreen.setKeepOnScreenCondition { !isReady }

            if (OsUtils.atLeastT) {
                NotificationUtils.PermissionState()
            }
            LocationManagerUtils.PermissionsState()

            MainScreen()
        }
    }
}