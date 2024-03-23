package com.sanmer.geomag.app.utils

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.core.location.GnssStatusCompat
import androidx.core.location.LocationListenerCompat
import androidx.core.location.LocationManagerCompat
import androidx.core.location.LocationRequestCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

object LocationManagerUtils {
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

    fun isEnabled(context: Context) =
        LocationManagerCompat.isLocationEnabled(context.locationManager)

    @SuppressLint("MissingPermission")
    fun getLocationAsFlow(context: Context) = callbackFlow {
        if (!(context.hasPermissions() && isEnabled(context))) {
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
        if (!(context.hasPermissions() && isEnabled(context))) {
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
}