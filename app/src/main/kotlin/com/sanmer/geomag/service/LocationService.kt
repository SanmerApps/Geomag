package com.sanmer.geomag.service

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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

class LocationService : LifecycleService() {
    val context by lazy { this }

    override fun onCreate() {
        super.onCreate()
        isRunning = true
        notifyStart()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && STOP_SERVICE == intent.action) {
            stopSelf()
            return START_NOT_STICKY
        }

        LocationManagerUtils.locationUpdates(this)
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                location = it
            }
            .launchIn(lifecycleScope)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        notifyCancel()
    }

    private fun notifyStart() {
        val self = Intent(this, LocationService::class.java).apply {
            action = STOP_SERVICE
        }
        val stopSelf = PendingIntent.getService(this, 0, self,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val mainActivity = Intent(this, MainActivity::class.java)
        val startMainActivity = PendingIntent.getActivity(this, 0, mainActivity,
            PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, NotificationUtils.CHANNEL_ID_LOCATION)
            .setSmallIcon(R.drawable.map_pin)
            .setContentTitle(getString(R.string.notification_name_location))
            .setContentText(getString(R.string.message_location_click))
            .setContentIntent(startMainActivity)
            .addAction(0, getString(R.string.action_stop), stopSelf)
            .setOngoing(true)
            .build()

        NotificationManagerCompat.from(this).apply {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) return

            notify(NotificationUtils.NOTIFICATION_ID_LOCATION, notification)
        }
    }

    private fun notifyCancel() {
        NotificationManagerCompat.from(this).apply {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) return

            cancel(NotificationUtils.NOTIFICATION_ID_LOCATION)
        }
    }

    companion object {
        private const val STOP_SERVICE = "STOP_LOCATION_SERVICE"

        var isRunning by mutableStateOf(false)
            private set

        var location by mutableStateOf(Location(LocationManager.GPS_PROVIDER))
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