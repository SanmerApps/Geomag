package com.sanmer.geomag.ui.activity.main

import android.os.Bundle
import com.sanmer.geomag.app.utils.LocationManagerUtils
import com.sanmer.geomag.app.utils.NotificationUtils
import com.sanmer.geomag.app.utils.OsUtils
import com.sanmer.geomag.ui.activity.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            if (OsUtils.atLeastT) {
                NotificationUtils.PermissionState()
            }

            LocationManagerUtils.PermissionsState()

            MainScreen()
        }
    }
}