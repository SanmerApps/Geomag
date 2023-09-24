package com.sanmer.geomag

import android.app.Application
import com.sanmer.geomag.app.utils.NotificationUtils
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
        app = this

        NotificationUtils.init(this)
    }

    companion object {
        private lateinit var app: App
        val context get() = app
    }
}