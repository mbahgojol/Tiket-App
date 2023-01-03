package binar.academy.flightgoadmin.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import binar.academy.flightgoadmin.utils.caption
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun ErrorMessage(modifier: Modifier = Modifier, msg: String) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.Url("https://assets4.lottiefiles.com/packages/lf20_gs9xrxtb.json"))
        LottieAnimation(composition = composition, modifier = Modifier.size(200.dp))
        SpacerHeight(height = 16.dp)
        Text(text = msg, style = caption)
    }
}