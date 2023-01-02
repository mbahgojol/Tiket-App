package com.rzl.flightgotiketbooking.ui.notif

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rzl.flightgotiketbooking.R
import com.rzl.flightgotiketbooking.ui.component.SpacerWidth
import com.rzl.flightgotiketbooking.utils.*


class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                NotificationScreen()
            }
        }
    }
}

@Composable
fun NotificationScreen(context: Context = LocalContext.current) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Notifications", style = largeTitle.copy(
                    color = Color.White, fontSize = 19.sp
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
                    tint = Color.White
                )
            }
        }, backgroundColor = colorResource(id = R.color.orange))
    }) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(it)
        ) {
            items(10) {
                Card(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Kode Transaksi : 00$it", style = caption.copy(
                                    fontSize = 12.sp
                                )
                            )
                            Text(
                                text = "Status Order :", style = caption.copy(
                                    fontSize = 12.sp
                                )
                            )
                        }

                        Row(
                            Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.garuda),
                                contentDescription = "",
                                Modifier
                                    .width(50.dp)
                                    .height(50.dp),
                                contentScale = ContentScale.Fit
                            )
                            SpacerWidth(width = 8.dp)
                            Text(
                                text = "Jakarta", style = largeTitleSemiBold.copy(
                                    fontSize = 10.sp,
                                )
                            )
                            Icon(Icons.Filled.ArrowRightAlt, contentDescription = "")
                            Text(
                                text = "Semarang", style = largeTitleSemiBold.copy(
                                    fontSize = 10.sp,
                                )
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            if (it == 2 || it == 5) {
                                ButtonCardNegative()
                            } else {
                                ButtonCardPositive()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemReward() {
    Card(
        elevation = 5.dp, shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Welcome reward upto")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            fontFamily = poppinsFamily,
                            color = colorResource(id = R.color.orange)
                        )
                    ) {
                        append(" 50%")
                    }
                }, style = largeTitleSemiBold.copy(
                    fontSize = 18.sp
                )
            )
            Text(
                text = "Get upto 50% discount on your\n" + "first booking.",
                style = largeTitle.copy(
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotifScreenPreview() {
    MaterialTheme {
        NotificationScreen()
    }
}

@Composable
fun ButtonCardPositive(click: () -> Unit = {}) {
    Button(
        onClick = click, colors = ButtonDefaults.buttonColors(
            backgroundColor = GreenFlight
        ), shape = CircleShape, modifier = Modifier.height(35.dp)
    ) {
        Icon(
            Icons.Filled.Check, contentDescription = "", tint = Color.White
        )
        SpacerWidth(width = 4.dp)
        Text(
            text = "Diterima", style = caption.copy(
                color = Color.White, fontSize = 10.sp
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
        ), shape = CircleShape, modifier = Modifier.height(35.dp)
    ) {
        Icon(
            Icons.Filled.Close, contentDescription = "", tint = Color.White
        )
        SpacerWidth(width = 4.dp)
        Text(
            text = "Ditolak", style = caption.copy(
                color = Color.White, fontSize = 10.sp
            )
        )
    }
}