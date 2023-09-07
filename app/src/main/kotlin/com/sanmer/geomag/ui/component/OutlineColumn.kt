package com.sanmer.geomag.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OutlineColumn(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) = Surface(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(15.dp),
    border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.outline),
    color = Color.Transparent
) {
    Column(
        modifier = Modifier.padding(contentPadding),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}