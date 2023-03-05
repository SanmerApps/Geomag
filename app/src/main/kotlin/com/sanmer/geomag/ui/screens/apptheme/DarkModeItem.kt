package com.sanmer.geomag.ui.screens.apptheme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanmer.geomag.R
import com.sanmer.geomag.app.Config

private sealed class DarkMode(
    val id: Int,
    val icon: Int,
    val name: Int
) {
    object Auto : DarkMode(
        id = Config.FOLLOW_SYSTEM,
        icon = R.drawable.auto_brightness_outline,
        name = R.string.settings_dark_theme_auto
    )
    object Light : DarkMode(
        id = Config.ALWAYS_OFF,
        icon = R.drawable.sun_outline,
        name = R.string.settings_dark_theme_light
    )
    object Dark : DarkMode(
        id = Config.ALWAYS_ON,
        icon = R.drawable.moon_outline,
        name = R.string.settings_dark_theme_dark
    )
}

private val modes = listOf(
    DarkMode.Auto,
    DarkMode.Light,
    DarkMode.Dark
)

@Composable
fun DarkModeItem() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(
            items = modes,
            key = { it.id }
        ) {
            DarkModeItem(
                item = it
            ) { id ->
                Config.DARK_MODE = id
            }
        }
    }
}

@Composable
private fun DarkModeItem(
    item: DarkMode,
    onClick: (Int) -> Unit
) {
    val selected = item.id == Config.DARK_MODE
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .clickable(
                onClick = {
                    onClick(item.id)
                },
            )
            .background(
                color = if (selected) {
                    MaterialTheme.colorScheme.surfaceVariant.copy(0.45f)
                } else {
                    MaterialTheme.colorScheme.outline.copy(0.1f)
                }
            ),
        contentAlignment = Alignment.Center
    ){
        Row(
            modifier = Modifier
                .padding(all = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(id = item.icon),
                contentDescription = null,
                tint = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    LocalContentColor.current
                }
            )
            Text(
                text = stringResource(id = item.name),
                style = MaterialTheme.typography.bodyMedium,
                color = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Unspecified
                }
            )
        }
    }
}