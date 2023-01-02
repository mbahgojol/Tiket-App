package com.rzl.flightgotiketbooking.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.rzl.flightgotiketbooking.R
import com.rzl.flightgotiketbooking.ui.component.MyDatePickerDialog
import com.rzl.flightgotiketbooking.ui.component.SpacerHeight
import com.rzl.flightgotiketbooking.ui.component.SpacerWidth
import com.rzl.flightgotiketbooking.ui.listflight.ListFlightActivity
import com.rzl.flightgotiketbooking.ui.notif.NotificationActivity
import com.rzl.flightgotiketbooking.utils.GreyFlight
import com.rzl.flightgotiketbooking.utils.caption
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

class SearchFlightData {
    var from by mutableStateOf("")
    var to by mutableStateOf("")
    var date by mutableStateOf("")
    var returnDate by mutableStateOf("")
    var adult by mutableStateOf("")
    var kgs by mutableStateOf("")
    var classFlight by mutableStateOf("")
}

@Composable
fun rememberSearchFlight() = remember {
    SearchFlightData()
}


class HomesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                HomeScreen()
            }
        }
    }
}

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val state = rememberLazyListState()

    Scaffold(modifier = modifier, topBar = {
        AppBar()
    }) {
        LazyColumn(
            contentPadding = it,
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.orange)),
        ) {

            item {
                LazyRow(
                    state = state,
                    flingBehavior = rememberSnapperFlingBehavior(lazyListState = state),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(10) {
                        ItemPromo()
                    }
                }
            }

            item {
                SearchFlight()
            }
        }
    }
}

@Composable
fun SearchFlight(context: Context = LocalContext.current) {
    val dataSearch = rememberSearchFlight()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(color = Color.White)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        FlightRoute(dataSearch)
        SpacerHeight(height = 16.dp)
        RoundTrip(dataSearch)
        SpacerHeight(height = 16.dp)
        PassengerSection(dataSearch)
        SpacerHeight(height = 16.dp)
        ClassFlight(dataSearch)
        SpacerHeight(height = 30.dp)
        Button(
            onClick = {
                context.apply {
                    Intent(this, ListFlightActivity::class.java).apply {
                        putExtra("from", dataSearch.from)
                        putExtra("to", dataSearch.to)
                        putExtra("adult", dataSearch.adult)
                        putExtra("class", dataSearch.classFlight)
                        startActivity(this)
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.orange), contentColor = Color.White
            )
        ) {
            Text(
                text = "Search Flights", style = caption.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        SpacerHeight(height = 100.dp)
    }
}

@Composable
fun ClassFlight(dataSearch: SearchFlightData) {
    val classFlight = listOf("Economy", "Business", "Elite")
    val classIcon = listOf(
        painterResource(id = R.drawable.economy),
        painterResource(id = R.drawable.business),
        painterResource(id = R.drawable.elite)
    )
    dataSearch.classFlight = classFlight[0]

    Column {
        Text(text = "Class", style = caption)
        SpacerHeight(height = 8.dp)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(classFlight.size, key = {
                classFlight[it]
            }) {
                ItemClass(
                    title = classFlight[it],
                    painter = classIcon[it],
                    modifier = Modifier.clickable {
                        dataSearch.classFlight = classFlight[it]
                    },
                    value = dataSearch.classFlight
                )
            }
        }
    }
}

@Composable
fun ItemClass(
    value: String = "",
    title: String = "Economy",
    painter: Painter = painterResource(id = R.drawable.elite),
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier
    ) {
        Icon(
            painter = painter,
            contentDescription = "",
            tint = if (value == title) colorResource(id = R.color.orange) else GreyFlight
        )
        SpacerWidth(width = 8.dp)
        Text(
            text = title, style = caption.copy(
                fontWeight = FontWeight.SemiBold,
                color = if (value == title) colorResource(id = R.color.orange) else GreyFlight
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemClassPreview() {
    ItemClass()
}

@Composable
fun PassengerSection(data: SearchFlightData) {
    Column {
        Text(text = "Passenger & Luggage", style = caption)
        Row {
            TextField(value = data.adult,
                onValueChange = { data.adult = it },
                label = { Text("Adult", style = caption) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                modifier = Modifier.weight(1f),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.adult),
                        contentDescription = "",
                        tint = colorResource(id = R.color.orange)
                    )
                })
        }
    }
}

@Composable
fun RoundTrip(data: SearchFlightData) {
    var withRoundTip by remember {
        mutableStateOf(false)
    }

    val mDatePickerDialogDepature = MyDatePickerDialog(listener = { y, m, mofday ->
        data.date = "$mofday/${m + 1}/$y"
    })

    val mDatePickerDialogReturn = MyDatePickerDialog(listener = { y, m, mofday ->
        data.returnDate = "$mofday/${m + 1}/$y"
    })

    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(value = data.date, onValueChange = { }, label = {
            Text(
                "Departure Date", style = caption.copy(
                    fontSize = 12.sp
                )
            )
        }, colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            disabledTextColor = Color.Black,
            focusedLabelColor = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high)
        ), modifier = Modifier
            .weight(1f)
            .clickable {
                mDatePickerDialogDepature.show()
            }, leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.date),
                contentDescription = "",
                tint = colorResource(id = R.color.orange)
            )
        }, readOnly = true, enabled = false
        )
        SpacerWidth(width = 8.dp)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Round Trip?", style = caption)
            Switch(
                checked = withRoundTip, onCheckedChange = {
                    withRoundTip = it
                }, colors = SwitchDefaults.colors(
                    checkedThumbColor = colorResource(id = R.color.orange)
                )
            )
        }
        SpacerWidth(width = 8.dp)

        Surface(modifier = Modifier.weight(1f)) {
            AnimatedVisibility(
                visible = withRoundTip
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    TextField(value = data.returnDate, onValueChange = { }, label = {
                        Text(
                            "Return Date", style = caption.copy(
                                fontSize = 12.sp
                            )
                        )
                    }, colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        disabledTextColor = Color.Black,
                        focusedLabelColor = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high)
                    ), textStyle = TextStyle(
                        textAlign = TextAlign.Right,
                    ), leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.date),
                            contentDescription = "",
                            tint = colorResource(id = R.color.orange)
                        )
                    }, enabled = false, readOnly = true, modifier = Modifier.clickable {
                        mDatePickerDialogReturn.show()
                    })
                }
            }
        }
    }
}

@Composable
fun FlightRoute(data: SearchFlightData) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(value = data.from,
            onValueChange = { data.from = it },
            label = { Text("From", style = caption) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White
            ),
            modifier = Modifier.weight(1f)
        )
        SpacerWidth(width = 8.dp)
        Image(
            painter = painterResource(id = R.drawable.flights), contentDescription = ""
        )
        SpacerWidth(width = 8.dp)
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            TextField(value = data.to,
                onValueChange = { data.to = it },
                label = { Text("To", style = caption) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(
                    textAlign = TextAlign.Right,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchFlightPreview() {
    SearchFlight()
}

@Preview(showBackground = true, widthDp = 200, heightDp = 150)
@Composable
fun ItemPromoPreview() {
    ItemPromo()
}

@Composable
fun ItemPromo() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Card(
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .width(screenWidth - 50.dp)
            .wrapContentHeight()
    ) {
        Image(
            painter = painterResource(id = R.drawable.slide_show),
            contentDescription = "",
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun AppBar(context: Context = LocalContext.current) {
    TopAppBar(
        backgroundColor = colorResource(id = R.color.orange), elevation = 0.dp
    ) {
        Image(painter = painterResource(id = R.drawable.app_logo), contentDescription = "")
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Image(
            painter = painterResource(id = R.drawable.man_user),
            contentDescription = "",
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        SpacerWidth(width = 8.dp)
        IconButton(onClick = {
            context.apply {
                val intent = Intent(this, NotificationActivity::class.java)
                startActivity(intent)
            }
        }) {
            Icon(
                Icons.Filled.NotificationsNone, contentDescription = "", tint = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    AppBar()
}

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}