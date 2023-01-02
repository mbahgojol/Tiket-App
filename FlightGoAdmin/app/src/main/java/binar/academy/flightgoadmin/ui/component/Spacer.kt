package binar.academy.flightgoadmin.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp

@Composable
fun SpacerHeight(
    modifier: Modifier = Modifier, height: Dp
) {
    Layout({}, modifier.height(height)) { _, constraints ->
        with(constraints) {
            val width = if (hasFixedWidth) maxWidth else 0
            val height = if (hasFixedHeight) maxHeight else 0
            layout(width, height) {}
        }
    }
}

@Composable
fun SpacerWidth(
    modifier: Modifier = Modifier, width: Dp
) {
    Layout({}, modifier.width(width)) { _, constraints ->
        with(constraints) {
            val width = if (hasFixedWidth) maxWidth else 0
            val height = if (hasFixedHeight) maxHeight else 0
            layout(width, height) {}
        }
    }
}