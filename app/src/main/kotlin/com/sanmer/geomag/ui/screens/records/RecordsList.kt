package com.sanmer.geomag.ui.screens.records

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sanmer.geomag.R
import com.sanmer.geomag.model.Record
import com.sanmer.geomag.ui.component.Logo
import com.sanmer.geomag.ui.component.scrollbar.VerticalFastScrollbar
import com.sanmer.geomag.ui.utils.navigateSingleTopTo
import com.sanmer.geomag.viewmodel.RecordViewModel

@Composable
fun RecordsList(
    list: List<Record>,
    state: LazyListState,
    isSelected: (Record) -> Boolean,
    isChooser: Boolean,
    setChooser: (Boolean) -> Unit,
    onToggle: (Record) -> Unit,
    navController: NavController,
) = Box(
    modifier = Modifier.fillMaxSize()
) {
    LazyColumn(
        state = state,
        modifier = Modifier.fillMaxSize()
    ) {
        items(list) {
            RecordItem(
                record = it,
                isSelected = isSelected(it),
                isChooser = isChooser,
                setChooser = setChooser,
                onToggle = onToggle,
                onClick = {
                    navController.navigateSingleTopTo(
                        RecordViewModel.putRecordKey(it)
                    )
                }
            )
        }
    }

    VerticalFastScrollbar(
        state = state,
        modifier = Modifier.align(Alignment.CenterEnd)
    )
}

@Composable
private fun RecordItem(
    record: Record,
    isSelected: Boolean,
    isChooser: Boolean,
    setChooser: (Boolean) -> Unit,
    onToggle: (Record) -> Unit,
    onClick: () -> Unit = {}
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .combinedClickable(
            enabled = true,
            onClick = {
                if (isChooser) {
                    onToggle(record)
                } else {
                    onClick()
                }
            },
            onLongClick = {
                if (!isChooser) {
                    setChooser(true)
                    onToggle(record)
                }
            },
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(
                bounded = true,
                color = MaterialTheme.colorScheme.primary
            )
        )
        .padding(all = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(16.dp)
) {
    if (isSelected) {
        Icon(
            modifier = Modifier.size(36.dp),
            painter = painterResource(id = R.drawable.circle_check_filled),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null
        )
    } else {
        Logo(
            text = record.model.name.uppercase(),
            modifier = Modifier.size(36.dp),
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = record.position.toString(),
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = record.time.toString(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}