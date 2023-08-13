package com.sanmer.geomag.app.utils

import android.Manifest.permission
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationListenerCompat
import androidx.core.location.LocationManagerCompat
import androidx.core.location.LocationRequestCompat
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.sanmer.geomag.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

object LocationManagerUtils {
    private lateinit var permissionsState: MultiplePermissionsState

    private val context by lazy { App.context }
    private val locationManager by lazy { context.getSystemService(Context.LOCATION_SERVICE) as LocationManager }

    var isReady by mutableStateOf(false)
        private set
    var isEnable by mutableStateOf(false)
        private set

    private val bestProvider: String
        get() = if (OsUtils.atLeastS) {
            LocationManager.FUSED_PROVIDER
        } else {
            LocationManager.GPS_PROVIDER
        }

    fun <T> update(callback: LocationManagerUtils.(Boolean) -> T): T {
        isEnable = LocationManagerCompat.isLocationEnabled(locationManager)

        @Suppress("UNUSED_EXPRESSION")
        return callback(isEnable)
    }

    init {
        update {
            Timber.d("isLocationEnabled: $it")
        }
    }

    @Composable
    fun PermissionsState(
        onGranted: () -> Unit = {},
        onDenied: () -> Unit = {},
        onInit: () -> Unit = {}
    ) {
        permissionsState = rememberMultiplePermissionsState(
            listOf(
                permission.ACCESS_FINE_LOCATION,
                permission.ACCESS_COARSE_LOCATION,
            )
        )

        val allPermissionsRevoked =
            permissionsState.permissions.size ==
                    permissionsState.revokedPermissions.size

        if (!allPermissionsRevoked) {
            onGranted()
            isReady = true
        } else if (permissionsState.shouldShowRationale) {
            onDenied()
        } else {
            onInit()
        }

        SideEffect {
            update {}
        }
    }

    fun launchPermissionRequest() = update {
        permissionsState.launchMultiplePermissionRequest()
    }

    fun getLastKnownLocation(): Location? = update { enable ->
        if (!enable) return@update null

        if (ActivityCompat.checkSelfPermission(
                context, permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context, permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@update null
        }

        Timber.d("LocationManagerUtils: getLastKnownLocation")
        return@update locationManager.getLastKnownLocation(bestProvider)
    }

    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    private fun requestLocationUpdates(listener: LocationListenerCompat) = update {
        if (!isEnable) return@update

        Timber.d("LocationManagerUtils: requestLocationUpdates")
        val locationRequest = LocationRequestCompat.Builder(1)
            .setQuality(LocationRequestCompat.QUALITY_HIGH_ACCURACY)
            .setMinUpdateDistanceMeters(0f)
            .build()

        LocationManagerCompat.requestLocationUpdates(
            locationManager,
            bestProvider,
            locationRequest,
            listener,
            Looper.getMainLooper()
        )
    }

    fun locationUpdates() = callbackFlow {
        if (ActivityCompat.checkSelfPermission(
                context, permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context, permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            close()
        }

        val listener = LocationListenerCompat {
            trySend(it)
        }

        try {
            requestLocationUpdates(listener)
        } catch (e: Exception) {
            Timber.e(e.message)
            close(e)
        }

        awaitClose {
            Timber.d("LocationManagerUtils: removeUpdates")
            LocationManagerCompat.removeUpdates(locationManager, listener)
        }
    }.flowOn(Dispatchers.Default)
}