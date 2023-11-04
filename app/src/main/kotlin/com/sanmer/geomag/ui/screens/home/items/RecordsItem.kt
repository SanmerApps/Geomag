package com.sanmer.geomag.ui.screens.home.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.ui.component.OverviewButton
import com.sanmer.geomag.ui.component.OverviewCard
import com.sanmer.geomag.ui.navigation.navigateToRecords

@Composable
fun RecordsItem(
    navController: NavController,
    enableRecords: Boolean,
    setEnableRecords: (Boolean) -> Unit
) = OverviewCard(
    expanded = true,
    icon = R.drawable.database,
    label = stringResource(id = R.string.page_records),
    trailingIcon = {
        Switch(
            checked = enableRecords,
            onCheckedChange = setEnableRecords
        )
    }
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        OverviewButton(
            onClick = { navController.navigateToRecords() },
            icon = R.drawable.database_search,
            text = stringResource(id = R.string.overview_view_records)
        )
    }
}