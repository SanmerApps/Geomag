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
import dev.sanmer.geomag.MagneticField

@Composable
fun MagneticFieldItem(
    value: MagneticField,
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
            value1 = "${value.f} nT",
            value2 = "${value.fDot} nT/yr"
        )

        ValueItem(
            key = stringResource(id = R.string.record_value_h),
            value1 = "${value.h} nT",
            value2 = "${value.hDot} nT/yr"
        )

        ValueItem(
            key = stringResource(id = R.string.record_value_x),
            value1 = "${value.x} nT",
            value2 = "${value.xDot} nT/yr"
        )

        ValueItem(
            key = stringResource(id = R.string.record_value_y),
            value1 = "${value.y} nT",
            value2 = "${value.yDot} nT/yr"
        )

        ValueItem(
            key = stringResource(id = R.string.record_value_z),
            value1 = "${value.z} nT",
            value2 = "${value.zDot} nT/yr"
        )

        ValueItem(
            key = stringResource(id = R.string.record_value_d),
            value1 = "${value.d} rad",
            value2 = "${value.dDot} rad/yr"
        )

        ValueItem(
            key = stringResource(id = R.string.record_value_i),
            value1 = "${value.i} rad",
            value2 = "${value.iDot} rad/yr"
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