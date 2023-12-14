package com.sanmer.geomag.ui.screens.home.items

import android.content.Context
import android.content.Intent
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
import com.sanmer.geomag.app.utils.LocationManagerUtils
import com.sanmer.geomag.model.data.Position
import com.sanmer.geomag.ui.component.OutlineColumn
import com.sanmer.geomag.ui.component.OverviewCard
import com.sanmer.geomag.ui.component.ValueItem

@Composable
fun LocationItem(
    isRunning: Boolean,
    position: Position,
    toggleLocation: (Context) -> Unit
) = OverviewCard(
    expanded = isRunning,
    icon = R.drawable.map_pin,
    label = stringResource(id = R.string.overview_location),
    trailingIcon = {
        val context = LocalContext.current

        IconButton(
            onClick = {
                if (!LocationManagerUtils.isEnable) {
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                } else if (LocationManagerUtils.isReady) {
                    toggleLocation(context)
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
        ValueItem(
            key = stringResource(id = R.string.overview_latitude),
            value = position.latitudeWithUnit
        )

        ValueItem(
            key = stringResource(id = R.string.overview_longitude),
            value = position.longitudeWithUnit
        )

        ValueItem(
            key = stringResource(id = R.string.overview_altitude),
            value = position.altitudeWithUnit
        )
    }
}