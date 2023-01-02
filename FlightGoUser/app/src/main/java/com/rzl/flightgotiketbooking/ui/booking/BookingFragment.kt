package com.rzl.flightgotiketbooking.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import com.rzl.flightgotiketbooking.R
import com.rzl.flightgotiketbooking.data.model.ResponseFlightList
import com.rzl.flightgotiketbooking.databinding.FragmentBookingBinding
import com.rzl.flightgotiketbooking.ui.component.CenterProgressBar
import com.rzl.flightgotiketbooking.ui.component.ErrorMessage
import com.rzl.flightgotiketbooking.ui.component.SpacerHeight
import com.rzl.flightgotiketbooking.ui.component.SpacerWidth
import com.rzl.flightgotiketbooking.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingFragment : Fragment() {

    private var binding: FragmentBookingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookingBinding.inflate(inflater, container, false)
        binding?.composeView?.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    BookingScreen()
                }
            }
        }
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}

@Composable
fun BookingScreen(viewModel: BookingViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getListFlight()
    }

    viewModel.uistateList.collectAsState().value.also {
        when (it) {
            is UiState.Loading -> {
                CenterProgressBar()
            }
            is UiState.Success -> {
                val response = it.data
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(id = R.color.white)),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(response.size) { i ->
                        ItemHistory(model = response[i])
                    }
                }
            }
            is UiState.Error -> {
                ErrorMessage(msg = it.errorMessage)
            }
        }
    }
}

@Composable
fun ItemHistory(model: ResponseFlightList.ResponseFlightListItem) {
    Card(
        Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = "12 Nov 2022", style = caption.copy(
                        fontSize = 12.sp
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = model.totalPrice.toString().toCurrencyFormat(), style = caption.copy(
                        fontSize = 12.sp
                    )
                )
            }
            SpacerHeight(height = 10.dp)
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mdi_flight), contentDescription = ""
                )
                SpacerWidth(width = 16.dp)
                Text(
                    text = model.kotaAsal, style = largeTitleSemiBold.copy(
                        fontSize = 12.sp,
                    )
                )
                Icon(Icons.Filled.ArrowRightAlt, contentDescription = "")
                Text(
                    text = model.kotaTujuan, style = largeTitleSemiBold.copy(
                        fontSize = 12.sp,
                    )
                )
            }
            SpacerHeight(height = 16.dp)
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Selesai",
                    style = caption.copy(
                        fontSize = 12.sp, color = Color.White
                    ),
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = GreenCard)
                        .padding(horizontal = 22.dp, vertical = 3.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Detail", style = caption.copy(
                        fontSize = 12.sp, color = colorResource(id = R.color.orange)
                    )
                )
                Icon(
                    Icons.Filled.ArrowRight,
                    contentDescription = "",
                    tint = colorResource(id = R.color.orange)
                )
            }
        }
    }
}

@Preview
@Composable
fun BookingScreenPreview() {
    MaterialTheme {
        BookingScreen()
    }
}
