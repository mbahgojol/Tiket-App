package binar.academy.flightgoadmin.ui.detailorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.findNavController
import binar.academy.flightgoadmin.R
import binar.academy.flightgoadmin.databinding.FragmentDetailOrderBinding
import binar.academy.flightgoadmin.ui.component.CenterProgressBar
import binar.academy.flightgoadmin.ui.component.ErrorMessage
import binar.academy.flightgoadmin.ui.component.SpacerHeight
import binar.academy.flightgoadmin.ui.customers.id
import binar.academy.flightgoadmin.utils.*
import coil.compose.rememberAsyncImagePainter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailOrderFragment : Fragment() {

    private var binding: FragmentDetailOrderBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailOrderBinding.inflate(inflater, container, false)
        binding?.composeView?.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                DetailOrderScreen(back = {
                    findNavController().navigateUp()
                })
            }
        }
        return binding?.root
    }
}

@Composable
fun DetailOrderScreen(
    back: () -> Unit,
    viewModel: DetailOrderViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getOrderById(id)
    }

    viewModel.uistateList.collectAsState().value.let {
        when (it) {
            is UiState.Error -> {
                ErrorMessage(msg = it.errorMessage)
            }
            is UiState.Success -> {
                val response = it.data
                Scaffold(topBar = {
                    TopAppBar(title = {
                        Text(
                            text = "Customer Order Detail", style = largeTitle.copy(
                                color = Color.Black, fontSize = 19.sp
                            )
                        )
                    }, navigationIcon = {
                        IconButton(onClick = {
                            back()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_new_24_black),
                                contentDescription = "",
                                tint = Color.Black
                            )
                        }
                    }, backgroundColor = colorResource(id = R.color.white))
                }) { pv ->
                    Column(
                        Modifier
                            .padding(pv)
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Bukti Pembayaran", style = largeTitleSemiBold)
                        SpacerHeight(height = 8.dp)
                        Text(
                            text = response.data.id.toString(), style = caption.copy(
                                color = OrangeFlight
                            )
                        )
                        SpacerHeight(height = 16.dp)
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = response.data.buktiPembayaran),
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }
                        SpacerHeight(height = 16.dp)
                    }
                }
            }
            is UiState.Loading -> {
                CenterProgressBar()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPrev() {
    MaterialTheme {
        DetailOrderScreen(back = {

        })
    }
}