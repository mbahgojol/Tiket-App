package com.rzl.flightgotiketbooking.ui.detailtiket

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.rzl.flightgotiketbooking.R
import com.rzl.flightgotiketbooking.data.model.ResponseFlightList
import com.rzl.flightgotiketbooking.ui.component.*
import com.rzl.flightgotiketbooking.ui.payment.PaymentActivity
import com.rzl.flightgotiketbooking.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTiketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra("id")
        val adultString = intent.getStringExtra("adult")
        val adult = if (adultString.isNullOrEmpty()) 1 else adultString.toInt()
        val classFlight = intent.getStringExtra("class")
        setContent {
            DetailTiketScreen(
                id?.toInt() ?: 0, adult = adult, classFlight = classFlight ?: "Economy"
            )
        }
    }
}

val data = mutableListOf<DataPassenger>()

@Composable
fun DetailTiketScreen(
    id: Int,
    viewModel: DetailTiketViewModel = hiltViewModel(),
    adult: Int = 1,
    classFlight: String = "Economy"
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getDetail(id)
    }

    val dataPassenger = remeberDataPassenger(adult)

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Passenger Details", style = largeTitle.copy(
                    color = Color.Black, fontSize = 19.sp
                )
            )
        }, navigationIcon = {
            IconButton(onClick = {
                context.apply {
                    (context as AppCompatActivity).finish()
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        }, backgroundColor = colorResource(id = R.color.white))
    }) { paddingv ->
        viewModel.uistate.collectAsState().value.also {
            when (it) {
                is UiState.Loading -> {
                    CenterProgressBar(Modifier.padding(paddingv))
                }
                is UiState.Success -> {
                    Column(
                        Modifier
                            .padding(paddingv)
                            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState())
                        ) {
                            SpacerHeight(height = 16.dp)
                            CardTiket(it.data, classFlight)
                            SpacerHeight(height = 16.dp)
                            PassengerDetail(adult = adult, dataPassenger)
                            SpacerHeight(height = 16.dp)
                        }
                        SpacerHeight(height = 16.dp)
                        ButtonNext(Modifier.fillMaxWidth(), click = {
                            context.apply {
                                Intent(this, PaymentActivity::class.java).apply {
                                    startActivity(this)
                                }
                            }
                        }, title = "Processed Continue")
                    }
                }
                is UiState.Error -> {
                    ErrorMessage(msg = it.errorMessage)
                }
            }
        }
    }
}

@Composable
fun ButtonNext(
    modifier: Modifier = Modifier, click: () -> Unit = {}, title: String = ""
) {
    Button(
        onClick = click,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.orange), contentColor = Color.White
        )
    ) {
        Text(
            text = title, style = caption.copy(
                fontSize = 16.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonNext() {
    ButtonNext()
}

@Composable
fun PassengerDetail(adult: Int = 1, dataPassenger: MutableList<DataPassenger>) {
    Column(Modifier.fillMaxSize()) {
        Text(
            text = "Passenger Details", style = largeTitleSemiBold.copy(
                fontSize = 14.sp
            )
        )
        (0..adult.minus(1)).forEach {
            SpacerHeight(height = 16.dp)
            ItemPassenger(
                "${it.plus(1)} : Passenger Details", dataPassenger = dataPassenger[it]
            )
        }
    }
}

@Composable
fun ItemPassenger(title: String, dataPassenger: DataPassenger) {
    var isExpand by remember {
        mutableStateOf(false)
    }
    val colorCard by remember {
        derivedStateOf {
            if (isExpand) GreyFlight else Color.White
        }
    }
    val colorTitle by remember {
        derivedStateOf {
            if (isExpand) Color.White else Color.Black
        }
    }

    val mDatePickerDialog = MyDatePickerDialog(listener = { y, m, mofday ->
        dataPassenger.date = "$mofday/${m + 1}/$y"
    })

    val source = remember {
        MutableInteractionSource()
    }

    if (source.collectIsPressedAsState().value) {
        mDatePickerDialog.show()
    }

    Card(
        Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(color = colorCard)
                    .clickable {
                        isExpand = !isExpand
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 14.dp),
                    text = title,
                    style = largeTitleSemiBold.copy(
                        fontSize = 16.sp, color = colorTitle
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Filled.ArrowDropDown, contentDescription = "", tint = colorTitle
                    )
                }
            }
            AnimatedVisibility(visible = isExpand) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = GreyCard)
                        .padding(16.dp)
                ) {
                    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                        value = dataPassenger.firtName,
                        onValueChange = {
                            dataPassenger.firtName = it
                        },
                        label = {
                            Text(text = "First Name")
                        },
                        placeholder = {
                            Text(text = "Please input first name in here")
                        })
                    SpacerHeight(height = 16.dp)
                    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                        value = dataPassenger.lastName,
                        onValueChange = {
                            dataPassenger.lastName = it
                        },
                        label = {
                            Text(text = "Last Name")
                        },
                        placeholder = {
                            Text(text = "Please input last name in here")
                        })
                    SpacerHeight(height = 16.dp)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = dataPassenger.date,
                        onValueChange = {},
                        label = {
                            Text(text = "Date of Birth")
                        },
                        placeholder = {
                            Text(text = "Please input date in here")
                        },
                        readOnly = true,
                        interactionSource = source,
                    )
                    SpacerHeight(height = 16.dp)
                    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                        value = dataPassenger.national,
                        onValueChange = {
                            dataPassenger.national = it
                        },
                        label = {
                            Text(text = "National")
                        },
                        placeholder = {
                            Text(text = "Please input national in here")
                        })
                }
            }
        }
    }
}

class DataPassenger {
    var firtName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var date by mutableStateOf("")
    var national by mutableStateOf("")
}

@Composable
fun remeberDataPassenger(size: Int) = remember {
    repeat((0..size.minus(1)).count()) {
        data.add(DataPassenger())
    }
    data
}

@Composable
fun CardTiket(data: ResponseFlightList.ResponseFlightListItem, classFlight: String = "Economy") {
    Card(
        modifier = Modifier.fillMaxWidth(), elevation = 1.dp, shape = RoundedCornerShape(10.dp)
    ) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mdi_flight), contentDescription = ""
                )
                SpacerWidth(width = 8.dp)
                Text(
                    text = "Min, 27 Nov 2022", style = largeTitleSemiBold.copy(
                        fontSize = 12.sp, color = GreyFlight
                    )
                )
            }
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = data.imageProduct),
                    contentDescription = "",
                    Modifier
                        .width(100.dp)
                        .height(50.dp),
                    contentScale = ContentScale.Fit
                )
                SpacerWidth(width = 8.dp)
                Text(
                    text = data.kotaAsal, style = largeTitleSemiBold.copy(
                        fontSize = 12.sp,
                    )
                )
                Icon(Icons.Filled.ArrowRightAlt, contentDescription = "")
                Text(
                    text = data.kotaTujuan, style = largeTitleSemiBold.copy(
                        fontSize = 12.sp,
                    )
                )
            }
            Text(
                text = "${data.depatureTime} - ${data.depatureTime_}", style = caption.copy(
                    color = GreyFlight
                )
            )
            Text(
                text = classFlight, style = caption.copy(
                    color = GreyFlight
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailTiketScreenPreview() {
    MaterialTheme {
        DetailTiketScreen(0, adult = 1)
    }
}