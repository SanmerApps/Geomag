package com.sanmer.geomag.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sanmer.geomag.utils.extensions.deleteLog
import com.sanmer.geomag.utils.log.Logcat
import timber.log.Timber

class LogcatReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                context.deleteLog(Logcat.FILE_NAME)
                Timber.i("boot-complete triggered")
            }
        }
    }
}