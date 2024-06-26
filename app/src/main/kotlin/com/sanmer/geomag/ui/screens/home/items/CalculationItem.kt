package com.sanmer.geomag.ui.screens.home.items

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sanmer.geomag.BuildConfig
import com.sanmer.geomag.Compat
import com.sanmer.geomag.R
import com.sanmer.geomag.ui.component.DropdownMenu
import com.sanmer.geomag.ui.component.OverviewButton
import com.sanmer.geomag.ui.component.OverviewCard
import com.sanmer.geomag.ui.navigation.graphs.HomeScreen
import com.sanmer.geomag.ui.utils.navigateSingleTopTo

@Composable
fun CalculationItem(
    navController: NavController,
    fieldModel: Compat.Models,
    setFieldModel: (Compat.Models) -> Unit,
    singleCalculate: () -> Unit
) = OverviewCard(
    expanded = true,
    icon = R.drawable.sum,
    label = stringResource(id = R.string.overview_calculation),
    trailingIcon = {
        ModelSelect(
            selected = fieldModel,
            onChange = setFieldModel
        )
    }
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        OverviewButton(
            onClick = singleCalculate,
            icon = R.drawable.player_play_filled,
            text = stringResource(id = R.string.overview_single),
        )

        OverviewButton(
            onClick = { navController.navigateSingleTopTo(HomeScreen.Customize.route) },
            icon = R.drawable.variable_plus,
            text = stringResource(id = R.string.overview_customized),
            enabled = BuildConfig.DEBUG
        )
    }
}

@Composable
private fun ModelSelect(
    selected: Compat.Models,
    onChange: (Compat.Models) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val animateZ by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "animateZ"
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        contentAlignment = Alignment.BottomEnd,
        surface = {
            FilterChip(
                selected = true,
                onClick = { expanded = true },
                label = { Text(text = selected.name) },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(16.dp)
                            .graphicsLayer {
                                rotationZ = animateZ
                            },
                        painter = painterResource(id = R.drawable.caret_down_filled),
                        contentDescription = null
                    )
                }
            )
        }
    ) {
        Compat.Models.entries.forEach {
            MenuItem(
                value = it.name,
                selected = selected.name,
                onClick = {
                    if (it.ordinal != selected.ordinal) onChange(it)
                    expanded = false
                }
            )
        }
    }
}

@Composable
private fun MenuItem(
    value: String,
    selected: String,
    onClick: () -> Unit
) = DropdownMenuItem(
    modifier = Modifier
        .background(
            if (value == selected) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                Color.Unspecified
            }
        ),
    text = { Text(text = value) },
    onClick = onClick
)