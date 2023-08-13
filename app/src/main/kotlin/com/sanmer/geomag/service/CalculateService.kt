package com.sanmer.geomag.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ServiceCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.sanmer.geomag.GeomagExt
import com.sanmer.geomag.R
import com.sanmer.geomag.app.utils.NotificationUtils
import com.sanmer.geomag.model.Position
import com.sanmer.geomag.model.Record
import com.sanmer.geomag.repository.LocalRepository
import com.sanmer.geomag.repository.UserDataRepository
import com.sanmer.geomag.ui.activity.main.MainActivity
import com.sanmer.geomag.utils.extensions.now
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CalculateService : LifecycleService() {
    @Inject
    lateinit var userDataRepository: UserDataRepository

    @Inject
    lateinit var localRepository: LocalRepository

    private val userData get() = userDataRepository.value

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

        parameters.sample(1000)
            .distinctUntilChanged()
            .onEach { (dataTime, position) ->
                currentValue = GeomagExt.run(
                    model = userData.fieldModel,
                    dataTime = dataTime,
                    position = position,
                )

                if (userData.enableRecords) {
                    localRepository.insert(currentValue)
                }
            }
            .launchIn(lifecycleScope + Dispatchers.Default)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
        isRunning = false
        ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
    }

    private fun setForeground() {
        val intent = Intent(this, CalculateService::class.java).apply {
            action = STOP_SERVICE
        }

        val stopSelf = PendingIntent.getService(this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationUtils
            .buildNotification(this, NotificationUtils.CHANNEL_ID_CALCULATE)
            .setSmallIcon(R.drawable.maze_oultine)
            .setContentTitle(getString(R.string.notification_name_calculate))
            .setContentText(getString(R.string.message_location_click))
            .setContentIntent(NotificationUtils.getActivity(MainActivity::class))
            .addAction(0, getString(R.string.action_stop), stopSelf)
            .setOngoing(true)
            .build()

        startForeground(NotificationUtils.NOTIFICATION_ID_CALCULATE, notification)
    }

    companion object {
        private const val STOP_SERVICE = "STOP_CALCULATE_SERVICE"

        private val parameters = MutableStateFlow(
            LocalDateTime.now() to Position.empty()
        )

        var isRunning by mutableStateOf(false)
            private set

        var currentValue by mutableStateOf(Record.empty())
            private set

        suspend fun updateParameters(
            dateTime: LocalDateTime,
            position: Position
        ) = withContext(Dispatchers.Default) {
            parameters.value = dateTime to position
        }

        fun start(context: Context) {
            val intent = Intent(context, CalculateService::class.java)
            context.startService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, CalculateService::class.java)
            context.stopService(intent)
        }
    }
}