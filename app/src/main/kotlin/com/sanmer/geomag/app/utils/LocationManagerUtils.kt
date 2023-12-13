package com.sanmer.geomag.app.utils

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
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

    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    private fun requestLocationUpdates(
        context: Context,
        listener:
        LocationListenerCompat
    ) = isLocationEnabled(context) {
        if (!isEnable) return@isLocationEnabled

        Timber.d("requestLocationUpdates")
        val locationRequest = LocationRequestCompat.Builder(1)
            .setQuality(LocationRequestCompat.QUALITY_HIGH_ACCURACY)
            .setMinUpdateDistanceMeters(0f)
            .build()

        LocationManagerCompat.requestLocationUpdates(
            context.locationManager,
            LocationManager.GPS_PROVIDER,
            locationRequest,
            listener,
            Looper.getMainLooper()
        )
    }

    @SuppressLint("MissingPermission")
    fun locationUpdates(context: Context) = callbackFlow {
        if (!context.hasPermissions()) {
            close()
        }

        val listener = LocationListenerCompat {
            trySend(it)
        }

        runCatching {
            requestLocationUpdates(context, listener)
        }.onFailure {
            Timber.e(it, "locationUpdates")
            close(it)
        }

        awaitClose {
            Timber.d("removeUpdates")
            LocationManagerCompat.removeUpdates(context.locationManager, listener)
        }
    }.flowOn(Dispatchers.Default)


}