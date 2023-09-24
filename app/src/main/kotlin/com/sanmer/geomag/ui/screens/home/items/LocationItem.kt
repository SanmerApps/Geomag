package com.sanmer.geomag.ui.screens.home.items

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanmer.geomag.R
import com.sanmer.geomag.app.utils.LocationManagerUtils
import com.sanmer.geomag.model.Position
import com.sanmer.geomag.ui.component.OutlineColumn
import com.sanmer.geomag.ui.component.OverviewCard
import com.sanmer.geomag.ui.component.ValueItem

@Composable
fun LocationItem(
    isRunning: Boolean,
    position: Position,
    modifier: Modifier = Modifier,
    toggleLocation: (Context) -> Unit
) = OverviewCard(
    expanded = isRunning,
    modifier = modifier,
    icon = R.drawable.location_outline,
    label = stringResource(id = R.string.overview_location),
    trailingIcon = {
        val context = LocalContext.current
        var show by remember { mutableStateOf(false) }
        if (show) NoAccessDialog(
            onClose = { show = false }
        )

        IconButton(
            onClick = {
                LocationManagerUtils.isLocationEnabled(context) {
                    if (!isEnable) {
                        show = true
                        return@isLocationEnabled
                    }

                    if (!isReady) {
                        launchPermissionRequest()
                        return@isLocationEnabled
                    }

                    toggleLocation(context)
                }
            }
        ) {
            Icon(
                painter = painterResource(id = if (isRunning) {
                    R.drawable.stop_outline
                } else {
                    R.drawable.play_outline
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

@Composable
private fun NoAccessDialog(
    onClose: () -> Unit
) = AlertDialog(
    onDismissRequest = onClose,
    title = {
        Text(text = stringResource(id = R.string.overview_no_location_access))
    },
    text = {
        Text(text = stringResource(id = R.string.overview_no_location_access_desc))
    },
    confirmButton = {
        val context = LocalContext.current
        TextButton(
            onClick = {
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                    context.startActivity(this)
                }
                onClose()
            }
        ) {
            Text(text = stringResource(id = R.string.overview_turn_on_location))
        }
    },
    dismissButton = {
        TextButton(
            onClick = onClose
        ) {
            Text(text = stringResource(id = R.string.dialog_close))
        }
    }
)