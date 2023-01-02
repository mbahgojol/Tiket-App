package binar.academy.flightgoadmin.ui.customers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.findNavController
import binar.academy.flightgoadmin.R
import binar.academy.flightgoadmin.data.model.ResponseTransaction
import binar.academy.flightgoadmin.databinding.FragmentCostumerBinding
import binar.academy.flightgoadmin.ui.component.*
import binar.academy.flightgoadmin.utils.*
import com.rzl.flightgotiketbooking.ui.component.Container
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CostumerFragment : Fragment() {

    private var binding: FragmentCostumerBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCostumerBinding.inflate(inflater, container, false)
        binding?.composeView?.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CustomerScreen(back = {
                    findNavController().navigateUp()
                },
                    clickToDetailOrder = {
                        findNavController().navigate(R.id.detailOrderFragment)
                    })
            }
        }
        return binding?.root
    }
}

var id = 0

@Composable
fun CustomerScreen(
    back: () -> Unit = {},
    clickToDetailOrder: () -> Unit = {},
    viewModel: CustomerViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getListTransaction()
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Card(
                Modifier.size(200.dp), shape = RoundedCornerShape(10.dp)
            ) {
                viewModel.stateStatusTrx.observeAsState().value?.let {
                    when (it) {
                        is UiState.Error -> {
                            Log.e("Error", it.errorMessage)
                            ErrorMessage(
                                msg = it.errorMessage, modifier = Modifier.padding(16.dp)
                            )
                        }
                        is UiState.Success -> {
                            Container(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .align(alignment = Alignment.Center),
                                    text = it.data.data.status,
                                    style = caption
                                )
                            }
                        }
                        is UiState.Loading -> {
                            CenterProgressBar(Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
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
                            text = "Customers", style = largeTitle.copy(
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
                    ) {
                        SpacerHeight(height = 16.dp)
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Lates Orders", style = largeTitleSemiBold.copy(
                                    fontSize = 14.sp, color = OrangeFlight
                                )
                            )
                        }
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(response.data.size) { i ->
                                ItemTrxCustomer(response.data[i], click = {
                                    showDialog = true
                                }, viewModel = viewModel,
                                    modifier = Modifier.clickable {
                                        clickToDetailOrder()
                                        id = response.data[i].id
                                    })
                            }
                        }
                    }
                }
            }
            is UiState.Loading -> {
                CenterProgressBar()
            }
        }
    }
}

@Composable
fun ItemTrxCustomer(
    data: ResponseTransaction.Data, viewModel: CustomerViewModel,
    click: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, shape = RoundedCornerShape(10.dp)
    ) {
        Column(Modifier.padding(vertical = 8.dp, horizontal = 12.dp)) {
            LineTextSection(text1 = data.id.toString(), text2 = data.createdAt)
            SpacerHeight(height = 4.dp)
            LineTextSection(text1 = data.userId.toString(), text2 = "Name")
            SpacerHeight(height = 4.dp)
            Text(
                text = data.productId.toString(), style = largeTitleSemiBold.copy(
                    fontSize = 15.sp
                )
            )
            SpacerHeight(height = 10.dp)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                ButtonCardPositive(click = {
                    click()
                    viewModel.accTrx(data.id)
                })
                SpacerWidth(width = 10.dp)
                ButtonCardNegative(click = {
                    click()
                    viewModel.rejectTrx(data.id)
                })
            }
        }
    }
}

@Composable
fun ButtonCardPositive(click: () -> Unit = {}) {
    Button(
        onClick = click, colors = ButtonDefaults.buttonColors(
            backgroundColor = GreenFlight
        )
    ) {
        Icon(
            Icons.Filled.Check, contentDescription = "", tint = Color.White
        )
        SpacerWidth(width = 4.dp)
        Text(
            text = "Accept", style = caption.copy(
                color = Color.White
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    Row {
        ButtonCardPositive()
        ButtonCardNegative()
    }
}

@Composable
fun ButtonCardNegative(click: () -> Unit = {}) {
    Button(
        onClick = click, colors = ButtonDefaults.buttonColors(
            backgroundColor = RedFlight
        )
    ) {
        Icon(
            Icons.Filled.Close, contentDescription = "", tint = Color.White
        )
        SpacerWidth(width = 4.dp)
        Text(
            text = "Reject", style = caption.copy(
                color = Color.White
            )
        )
    }
}

@Composable
fun LineTextSection(text1: String, text2: String) {
    Row(
        Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text1, style = largeTitleSemiBold.copy(
                fontSize = 15.sp
            )
        )
        SpacerWidth(width = 10.dp)
        Line(Modifier.width(50.dp))
        SpacerWidth(width = 10.dp)
        Text(
            text = text2, style = caption.copy(
                fontSize = 15.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {

}

@Preview(showBackground = true)
@Composable
fun CustomerScreenPreview() {
    MaterialTheme {
        CustomerScreen()
    }
}