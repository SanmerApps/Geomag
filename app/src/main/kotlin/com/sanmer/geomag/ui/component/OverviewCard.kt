package com.sanmer.geomag.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun OverviewCard(
    expanded: Boolean,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit = {}
) = Surface(
    modifier = modifier,
    shape = RoundedCornerShape(20.dp),
    color = MaterialTheme.colorScheme.surface,
    tonalElevation = 1.dp
) {
    Column(
        modifier = Modifier
            .animateContentSize(spring(stiffness = Spring.StiffnessMediumLow)),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                leadingIcon()
                Spacer(modifier = Modifier.width(16.dp))
            }

            Text(
                modifier = Modifier.weight(1f),
                text = label,
                style = MaterialTheme.typography.titleMedium
            )

            trailingIcon?.let {
                Spacer(modifier = Modifier.width(16.dp))
                trailingIcon()
            }
        }

        AnimatedVisibility(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            visible = expanded,
            enter = fadeIn(spring(stiffness = Spring.StiffnessMedium)),
            exit = fadeOut(spring(stiffness = Spring.StiffnessMedium)),
        ) {
            ProvideTextStyle(
                value = MaterialTheme.typography.bodyLarge,
                content = content
            )
        }
    }
}

@Composable
fun OverviewCard(
    expanded: Boolean,
    @DrawableRes icon: Int,
    label: String,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit = {}
) = OverviewCard(
    expanded = expanded,
    modifier = modifier,
    label = label,
    leadingIcon = {
        Logo(
            modifier = Modifier.size(40.dp),
            icon = icon,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        )
    },
    trailingIcon = trailingIcon,
    content = content
)

@Suppress("UnusedReceiverParameter")
@Composable
fun ColumnScope.ValueItem(
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

@Composable
fun OverviewButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    @DrawableRes icon: Int,
    text: String
) = Button(
    onClick = onClick,
    enabled = enabled
) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = null,
        modifier = Modifier.size(SuggestionChipDefaults.IconSize)
    )

    Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))

    Text(text = text)
}