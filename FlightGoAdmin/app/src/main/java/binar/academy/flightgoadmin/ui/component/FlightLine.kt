package binar.academy.flightgoadmin.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import binar.academy.flightgoadmin.R

@Composable
fun FlightLine(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .width(150.dp)
            .height(50.dp)
    ) {
        val (dot1, dot2, dotted, img) = createRefs()

        Dot(modifier = Modifier
            .size(24.dp)
            .constrainAs(dot1) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            })
        Dotted(modifier = Modifier.constrainAs(dotted) {
            top.linkTo(dot1.top)
            start.linkTo(dot1.end)
            end.linkTo(dot2.start)
            bottom.linkTo(dot1.bottom)
            width = Dimension.fillToConstraints
        })
        Image(painter = painterResource(id = R.drawable.ic_plane),
            contentDescription = "",
            modifier = Modifier
                .size(60.dp)
                .constrainAs(img) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
        Dot(modifier = Modifier
            .size(24.dp)
            .constrainAs(dot2) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            })
    }
}

@Preview(showBackground = true)
@Composable
fun FlightLinePreview() {
    MaterialTheme {
        FlightLine()
    }
}