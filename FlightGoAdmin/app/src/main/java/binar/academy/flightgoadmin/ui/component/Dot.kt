package binar.academy.flightgoadmin.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import binar.academy.flightgoadmin.utils.GreyFlight

@Composable
fun Dot(modifier: Modifier) {
    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawCircle(
            color = GreyFlight,
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
            radius = size.minDimension / 4
        )
    }
}

@Preview
@Composable
fun DotPreview() {
    Dot(modifier = Modifier)
}