package binar.academy.flightgoadmin.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.tooling.preview.Preview
import binar.academy.flightgoadmin.utils.GreyFlight

@Composable
fun Dotted(modifier: Modifier = Modifier) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Canvas(
        modifier = modifier
    ) {
        drawLine(
            color = GreyFlight,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect,
            strokeWidth = 5f
        )
    }
}

@Preview
@Composable
fun DottedPreview() {
    Dotted()
}