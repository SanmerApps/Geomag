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
import com.sanmer.geomag.model.data.MagneticFieldExt

@Composable
fun MagneticFieldItem(
    value: MagneticFieldExt,
    modifier: Modifier = Modifier
) = OutlinedCard(
    modifier = modifier,
    shape = RoundedCornerShape(15.dp)
) {
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ValueItem(
            key = stringResource(id = R.string.record_value_f),
            value1 = "${value.totalIntensity} nT",
            value2 = "${value.totalSV} nT/yr"
        )

        ValueItem(
            key = stringResource(id = R.string.record_value_h),
            value1 = "${value.horizontalIntensity} nT",
            value2 = "${value.horizontalSV} nT/yr"
        )

        ValueItem(
            key = stringResource(id = R.string.record_value_x),
            value1 = "${value.northComponent} nT",
            value2 = "${value.northSV} nT/yr"
        )

        ValueItem(
            key = stringResource(id = R.string.record_value_y),
            value1 = "${value.eastComponent} nT",
            value2 = "${value.eastSV} nT/yr"
        )

        ValueItem(
            key = stringResource(id = R.string.record_value_z),
            value1 = "${value.verticalComponent} nT",
            value2 = "${value.verticalSV} nT/yr"
        )

        ValueItem(
            key = stringResource(id = R.string.record_value_d),
            value1 = "${value.declination} rad",
            value2 = "${value.declinationSV} rad/yr"
        )

        ValueItem(
            key = stringResource(id = R.string.record_value_i),
            value1 = "${value.inclination} rad",
            value2 = "${value.inclinationSV} rad/yr"
        )
    }
}

@Composable
private fun ValueItem(
    key: String,
    value1: String,
    value2: String,
) = Column(
    modifier = Modifier.fillMaxWidth()
) {
    Text(
        text = key,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.outline
    )

    Text(
        text = value1,
        style = MaterialTheme.typography.bodyLarge
    )

    Text(
        text = value2,
        style = MaterialTheme.typography.bodyLarge
    )
}