package binar.academy.flightgoadmin.ui.component

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rzl.flightgotiketbooking.ui.component.Container

@Composable
fun CenterProgressBar(modifier: Modifier = Modifier) {
    Container(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .wrapContentSize()
                .align(alignment = Alignment.Center)
        )
    }
}