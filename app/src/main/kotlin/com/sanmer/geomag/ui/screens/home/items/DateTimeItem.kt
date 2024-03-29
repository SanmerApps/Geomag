package com.sanmer.geomag.ui.screens.home.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanmer.geomag.R
import com.sanmer.geomag.model.origin.DateTime
import com.sanmer.geomag.ui.component.OutlineColumn
import com.sanmer.geomag.ui.component.OverviewCard
import com.sanmer.geomag.ui.component.ValueItem

@Composable
fun DateTimeItem(
    isRunning: Boolean,
    dateTime: DateTime,
    toggleDateTime: () -> Unit
) = OverviewCard(
    expanded = true,
    icon = R.drawable.clock,
    label = stringResource(id = R.string.overview_datetime),
    trailingIcon = {
        IconButton(
            onClick = toggleDateTime
        ) {
            Icon(
                painter = painterResource(id = if (isRunning) {
                    R.drawable.player_pause
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
            key = stringResource(id = R.string.overview_datetime),
            value = dateTime.local
        )

        ValueItem(
            key = stringResource(id = R.string.overview_decimal),
            value = dateTime.decimalOfLocal
        )
    }
}