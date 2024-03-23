package com.sanmer.geomag.ui.screens.home.items

import android.content.Context
import android.content.Intent
import android.location.Location
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanmer.geomag.R
import com.sanmer.geomag.compat.LocationManagerCompat
import com.sanmer.geomag.ui.component.OutlineColumn
import com.sanmer.geomag.ui.component.OverviewCard
import com.sanmer.geomag.ui.component.ValueItem

@Composable
fun LocationItem(
    isRunning: Boolean,
    location: Location,
    toggleLocation: (Context) -> Unit
) = OverviewCard(
    expanded = isRunning,
    icon = R.drawable.map_pin,
    label = stringResource(id = R.string.overview_location),
    trailingIcon = {
        val context = LocalContext.current

        IconButton(
            onClick = {
                when {
                    !LocationManagerCompat.isEnabled(context) -> {
                        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                    else -> {
                        toggleLocation(context)
                    }
                }
            }
        ) {
            Icon(
                painter = painterResource(id = if (isRunning) {
                    R.drawable.player_stop
                } else {
                    R.drawable.player_play
                }),
                contentDescription = null
            )
        }
    }
) {
    OutlineColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(all = 12.dp)
    ) {
        with(location) {
            ValueItem(
                key = stringResource(id = R.string.overview_latitude),
                value = "${latitude}º N"
            )

            ValueItem(
                key = stringResource(id = R.string.overview_longitude),
                value = "${longitude}º W"
            )

            ValueItem(
                key = stringResource(id = R.string.overview_altitude),
                value = "$altitude m"
            )
        }
    }
}