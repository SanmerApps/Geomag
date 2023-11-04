package com.sanmer.geomag.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanmer.geomag.R
import com.sanmer.geomag.model.data.Record
import com.sanmer.geomag.ui.screens.records.view.items.MagneticFieldItem
import com.sanmer.geomag.ui.screens.records.view.items.RecordInfoItem
import com.sanmer.geomag.ui.utils.expandedShape

@Composable
fun RecordBottomSheet(
    record: Record,
    onClose: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = bottomSheetState,
        shape = BottomSheetDefaults.expandedShape(15.dp),
        windowInsets = WindowInsets.navigationBars
    ) {
        Text(
            text = stringResource(id = R.string.overview_mf),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        ) {
            RecordInfoItem(record = record)

            Spacer(modifier = Modifier.height(16.dp))

            MagneticFieldItem(value = record.values)
        }
    }
}