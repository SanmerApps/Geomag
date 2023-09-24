package com.sanmer.geomag

import android.app.Application
import com.sanmer.geomag.app.utils.LocationManagerUtils
import com.sanmer.geomag.app.utils.NotificationUtils
import com.sanmer.geomag.utils.extensions.deleteJson
import com.sanmer.geomag.utils.timber.DebugTree
import com.sanmer.geomag.utils.timber.ReleaseTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    init {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    override fun onCreate() {
        super.onCreate()

        NotificationUtils.init(this)
        LocationManagerUtils.init(this)

        deleteJson()
    }
}