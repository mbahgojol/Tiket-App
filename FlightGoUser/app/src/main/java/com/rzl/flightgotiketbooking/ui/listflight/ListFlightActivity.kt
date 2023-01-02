package com.rzl.flightgotiketbooking.ui.listflight

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.rzl.flightgotiketbooking.R
import com.rzl.flightgotiketbooking.data.model.ResponseFlightList
import com.rzl.flightgotiketbooking.databinding.ActivityAvailableFlightBinding
import com.rzl.flightgotiketbooking.ui.component.*
import com.rzl.flightgotiketbooking.ui.detailtiket.DetailTiketActivity
import com.rzl.flightgotiketbooking.utils.UiState
import com.rzl.flightgotiketbooking.utils.caption
import com.rzl.flightgotiketbooking.utils.largeTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFlightActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAvailableFlightBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvailableFlightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarBack.setOnClickListener {
            finish()
        }

        val from = intent.getStringExtra("from") ?: "Jakarta"
        val to = intent.getStringExtra("to") ?: "Semarang"
        val adult = intent.getStringExtra("adult") ?: "1"
        val classFlight = intent.getStringExtra("class") ?: "Economy"

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    ListFlight(
                        from = from, to = to, adult = adult, classFlight = classFlight
                    )
                }
            }
        }
    }
}

@Composable
fun ListFlight(
    viewModel: ListFlightViewModel = hiltViewModel(),
    from: String,
    to: String,
    adult: String = "1",
    classFlight: String = "Economy",
    context: Context = LocalContext.current
) {
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
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TheRoute(city = from)
                            SpacerWidth(width = 16.dp)
                            FlightLine(
                                modifier = Modifier
                                    .weight(1f)
                                    .wrapContentHeight()
                            )
                            SpacerWidth(width = 16.dp)
                            TheRoute(
                                horizontalAlignment = Alignment.End, city = to
                            )
                        }
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 30.dp, topEnd = 30.dp
                                    )
                                )
                                .background(color = colorResource(id = R.color.orange))
                                .padding(16.dp)
                        ) {
                            response.forEach { model ->
                                ItemFlight(classFlight = classFlight,
                                    model = model,
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .clickable {
                                            context.apply {
                                                Intent(
                                                    this, DetailTiketActivity::class.java
                                                ).apply {
                                                    putExtra("id", model.id.toString())
                                                    putExtra("adult", adult)
                                                    putExtra("class", classFlight)
                                                    startActivity(this)
                                                }
                                            }
                                        })
                                SpacerHeight(height = 16.dp)
                            }
                        }
                    }
                }
            }
            is UiState.Error -> {
                ErrorMessage(msg = it.errorMessage)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemFlightPreview() {
    ItemFlight()
}

@Composable
fun ItemFlight(
    modifier: Modifier = Modifier,
    model: ResponseFlightList.ResponseFlightListItem? = null,
    classFlight: String = "Economy",
) {
    Card(
        shape = RoundedCornerShape(
            30.dp
        ), modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 30.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TheRoute(
                    city = model?.kotaAsal.toString(), code = model?.kodeNegaraAsal.toString()
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = rememberAsyncImagePainter(model = model?.imageProduct),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                TheRoute(
                    city = model?.kotaTujuan.toString(),
                    code = model?.kodeNegaraTujuan.toString(),
                    horizontalAlignment = Alignment.End
                )
            }
            SpacerHeight(height = 10.dp)
            Row(modifier = Modifier.fillMaxWidth()) {
                DoubleDescription(
                    label = "Depart", value = model?.depatureTime.plus(" PM")
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                DoubleDescription(
                    label = "Flight Type",
                    value = model?.jenisPenerbangan.toString(),
                    horizontalAlignment = Alignment.End
                )
            }
            SpacerHeight(height = 8.dp)
            FlightLine(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            SpacerHeight(height = 16.dp)
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Rp 1.200.000", style = caption.copy(
                        fontSize = 18.sp, color = colorResource(id = R.color.orange)
                    )
                )
                SpacerWidth(width = 8.dp)
                Text(text = classFlight, style = caption)
            }
        }
    }
}

@Composable
fun DoubleDescription(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(
        modifier = modifier.wrapContentSize(), horizontalAlignment = horizontalAlignment
    ) {
        Text(text = label, style = caption)
        Text(
            text = value, style = caption.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
fun TheRoute(
    modifier: Modifier = Modifier,
    city: String = "Jakarta",
    code: String = "JKT",
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(
        modifier = modifier.wrapContentSize(), horizontalAlignment = horizontalAlignment
    ) {
        Text(text = city, style = caption)
        Text(text = code, style = largeTitle)
    }
}

@Preview(showBackground = true)
@Composable
fun ListFlightPreview() {
    MaterialTheme {
        ListFlight(from = "", to = "", adult = "1")
    }
}

