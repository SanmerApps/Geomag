package com.sanmer.geomag.app.utils

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.location.OnNmeaMessageListener
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.location.GnssStatusCompat
import androidx.core.location.LocationListenerCompat
import androidx.core.location.LocationManagerCompat
import androidx.core.location.LocationRequestCompat
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

object LocationManagerUtils {
    var isReady by mutableStateOf(false)
        private set
    var isEnable by mutableStateOf(false)
        private set

    private val Context.locationManager get() = checkNotNull(
        ContextCompat.getSystemService(this, LocationManager::class.java)
    )

    private fun Context.hasPermissions(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this, permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this, permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                )
    }

    fun <T> isLocationEnabled(context: Context, callback: LocationManagerUtils.() -> T): T {
        isEnable = LocationManagerCompat.isLocationEnabled(context.locationManager)

        @Suppress("UNUSED_EXPRESSION")
        return callback()
    }

    fun init(context: Context) {
        isLocationEnabled(context) {
            Timber.d("isLocationEnabled: $isEnable")
        }
    }

    @Composable
    fun PermissionsState() {
        val permissionsState = rememberMultiplePermissionsState(
            listOf(
                permission.ACCESS_FINE_LOCATION,
                permission.ACCESS_COARSE_LOCATION,
            )
        )

        val allPermissionsRevoked =
            permissionsState.permissions.size ==
                    permissionsState.revokedPermissions.size

        if (!allPermissionsRevoked) {
            isReady = true
        }

        SideEffect {
            permissionsState.launchMultiplePermissionRequest()
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocationAsFlow(context: Context) = callbackFlow {
        if (!(context.hasPermissions() && isEnable)) {
            close()
        }

        val locationManager = context.locationManager
        val listener = LocationListenerCompat {
            trySend(it)
        }

        val locationRequest = LocationRequestCompat.Builder(1)
            .setQuality(LocationRequestCompat.QUALITY_HIGH_ACCURACY)
            .setMinUpdateDistanceMeters(0f)
            .build()

        runCatching {
            Timber.d("requestLocationUpdates")
            LocationManagerCompat.requestLocationUpdates(
                locationManager,
                LocationManager.GPS_PROVIDER,
                locationRequest,
                listener,
                Looper.getMainLooper()
            )
        }.onFailure {
            Timber.e(it, "getLocationAsFlow")
            close(it)
        }

        awaitClose {
            Timber.d("removeUpdates")
            LocationManagerCompat.removeUpdates(locationManager, listener)
        }

    }.flowOn(Dispatchers.Default)

    @SuppressLint("MissingPermission")
    fun getGnssStatusAsFlow(context: Context) = callbackFlow {
        if (!(context.hasPermissions() && isEnable)) {
            close()
        }

        val locationManager = context.locationManager
        val callback = object : GnssStatusCompat.Callback() {
            override fun onSatelliteStatusChanged(status: GnssStatusCompat) {
                trySend(status)
            }
        }

        runCatching {
            Timber.d("registerGnssStatusCallback")
            LocationManagerCompat.registerGnssStatusCallback(
                locationManager,
                callback,
                Handler(Looper.getMainLooper())
            )
        }.onFailure {
            Timber.e(it, "getGnssStatusAsFlow")
            close(it)
        }

        awaitClose {
            Timber.d("unregisterGnssStatusCallback")
            LocationManagerCompat.unregisterGnssStatusCallback(locationManager, callback)
        }

    }.flowOn(Dispatchers.Default)

    @SuppressLint("MissingPermission")
    fun getAltitudeMeanSeaLevel(context: Context) = callbackFlow {
        if (!(context.hasPermissions() && isEnable)) {
            close()
        }

        val locationManager = context.locationManager
        val listener = OnNmeaMessageListener { message, _ ->
            if (NMEA_KEYS.any { message.startsWith(it) }) {
                val mslAltitudeMeters = message.split(",")
                    .getOrNull(MSL_ALTITUDE_INDEX)
                    ?.toDoubleOrNull()

                if (mslAltitudeMeters != null) {
                    trySend(mslAltitudeMeters)
                }
            }
        }

        runCatching {
            Timber.d("addNmeaListener")
            locationManager.addNmeaListener(
                listener,
                Handler(Looper.getMainLooper())
            )
        }.onFailure {
            Timber.e(it, "getNmeaAsFlow")
            close(it)
        }

        awaitClose {
            Timber.d("removeNmeaListener")
            locationManager.removeNmeaListener(listener)
        }

    }.flowOn(Dispatchers.Default)

    private const val MSL_ALTITUDE_INDEX = 9
    private val NMEA_KEYS = listOf("\$GPGGA", "\$GNGNS", "\$GNGGA")
}