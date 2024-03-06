package com.sanmer.geomag.ui.screens.records.view.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanmer.geomag.R
import com.sanmer.geomag.model.origin.Record

@Composable
fun RecordInfoItem(
    record: Record,
    modifier: Modifier = Modifier
) = OutlinedCard(
    modifier = modifier,
    shape = RoundedCornerShape(15.dp)
) {
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ValueItem(
            key = stringResource(id = R.string.record_info_model),
            value = record.model.name
        )

        with(record.location) {
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

        ValueItem(
            key = stringResource(id = R.string.overview_datetime),
            value = record.time.local
        )

        ValueItem(
            key = stringResource(id = R.string.overview_decimal),
            value = record.time.decimalOfLocal
        )
    }
}

@Composable
private fun ValueItem(
    key: String,
    value: Any,
) = Column(
    modifier = Modifier.fillMaxWidth()
) {
    Text(
        text = key,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.outline
    )

    Text(
        text = value.toString(),
        style = MaterialTheme.typography.bodyLarge
    )
}