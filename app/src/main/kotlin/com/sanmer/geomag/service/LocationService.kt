package com.sanmer.geomag.service

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.core.location.GnssStatusCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sanmer.geomag.app.utils.LocationManagerUtils
import com.sanmer.geomag.model.gnss.Satellite
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationService : LifecycleService() {
    override fun onCreate() {
        super.onCreate()
        isRunning = true

        LocationManagerUtils.getLocationAsFlow(this)
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                location = it
            }
            .launchIn(lifecycleScope)

        LocationManagerUtils.getGnssStatusAsFlow(this)
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                gnssStatusOrNull = it
            }
            .launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    companion object {
        var isRunning by mutableStateOf(false)
            private set

        var location by mutableStateOf(Location(LocationManager.GPS_PROVIDER))
            private set

        var gnssStatusOrNull: GnssStatusCompat? by mutableStateOf(null)
            private set
        val gnssStatus get() = checkNotNull(gnssStatusOrNull)

        fun getSatelliteList(): List<Satellite> {
            if (gnssStatusOrNull == null) {
                return emptyList()
            }

            return List(gnssStatus.satelliteCount) {
                Satellite(gnssStatus, it)
            }.toMutableStateList()
        }

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