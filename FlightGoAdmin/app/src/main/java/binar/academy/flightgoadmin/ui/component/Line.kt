package binar.academy.flightgoadmin.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun Line(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val canvasWidth = size.width

        drawLine(
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = canvasWidth, y = 0f),
            color = Color.Black,
            strokeWidth = 2F
        )
    }
}

@Preview
@Composable
fun LinePreview() {
    Line()
}