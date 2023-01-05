package com.sanmer.geomag.utils.timber

import android.util.Log
import timber.log.Timber

class ReleaseTree : Timber.DebugTree() {
    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return when(priority) {
            Log.VERBOSE -> true
            Log.DEBUG -> true
            Log.INFO -> true
            Log.WARN -> true
            Log.ERROR -> true
            else -> false
        }
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (!isLoggable(tag, priority)) {
            return
        }

        super.log(priority, "Geomag", message, t)
    }
}