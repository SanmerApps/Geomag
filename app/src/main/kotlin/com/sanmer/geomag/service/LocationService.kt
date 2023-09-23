package com.sanmer.geomag.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ServiceCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sanmer.geomag.R
import com.sanmer.geomag.app.utils.LocationManagerUtils
import com.sanmer.geomag.app.utils.NotificationUtils
import com.sanmer.geomag.ui.activity.MainActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class LocationService : LifecycleService() {
    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")
        isRunning = true
        setForeground()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && STOP_SERVICE == intent.action) {
            stopSelf()
            return START_NOT_STICKY
        }

        LocationManagerUtils.locationUpdates()
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                location = it
            }
            .launchIn(lifecycleScope)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
        isRunning = false
        ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
    }

    private fun setForeground() {
        val intent = Intent(this, LocationService::class.java).apply {
            action = STOP_SERVICE
        }

        val stopSelf = PendingIntent.getService(this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationUtils
            .buildNotification(this, NotificationUtils.CHANNEL_ID_LOCATION)
            .setSmallIcon(R.drawable.location_outline)
            .setContentTitle(getString(R.string.notification_name_location))
            .setContentText(getString(R.string.message_location_click))
            .setContentIntent(NotificationUtils.getActivity(MainActivity::class))
            .addAction(0, getString(R.string.action_stop), stopSelf)
            .setOngoing(true)
            .build()

        startForeground(NotificationUtils.NOTIFICATION_ID_LOCATION, notification)
    }

    companion object {
        private const val STOP_SERVICE = "STOP_LOCATION_SERVICE"

        var isRunning by mutableStateOf(false)
            private set

        var location by mutableStateOf(LocationManagerUtils.getLastKnownLocation()
            ?: Location(LocationManager.GPS_PROVIDER)
        )
            private set

        fun start(context: Context) {
            val intent = Intent(context, LocationService::class.java)
            context.startService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, LocationService::class.java)
            context.stopService(intent)
        }
    }
}