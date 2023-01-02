package com.rzl.flightgotiketbooking.ui.boardingpass

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rzl.flightgotiketbooking.R
import com.rzl.flightgotiketbooking.ui.MainActivity
import com.rzl.flightgotiketbooking.ui.component.Dotted
import com.rzl.flightgotiketbooking.ui.component.Line
import com.rzl.flightgotiketbooking.ui.component.SpacerHeight
import com.rzl.flightgotiketbooking.ui.detailtiket.ButtonNext
import com.rzl.flightgotiketbooking.ui.detailtiket.data
import com.rzl.flightgotiketbooking.utils.*

class BoardingPassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoardingPassScreen()
        }
    }
}

@Composable
fun BoardingPassScreen() {
    val context = LocalContext.current
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Boarding Pass", style = largeTitle.copy(
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
    }) {
        Column(
            Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = GreyCard)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(id = R.drawable.logo_splash),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(color = Color.Black),
                    contentScale = ContentScale.Fit
                )
                SpacerHeight(height = 16.dp)
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(Modifier.wrapContentSize()) {
                        Text(text = "UPG", style = largeTitleSemiBold)
                        Text(
                            text = "18.00", style = caption.copy(
                                color = GreyFlight
                            )
                        )
                    }
                    Column(
                        Modifier.wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_plane),
                            contentDescription = "",
                            modifier = Modifier.size(35.dp)
                        )
                        Text(text = "1h 30min", style = caption)
                    }
                    Column(Modifier.wrapContentSize()) {
                        Text(text = "SUB", style = largeTitleSemiBold)
                        Text(
                            text = "19.00", style = caption.copy(
                                color = GreyFlight
                            )
                        )
                    }
                }
                SpacerHeight(height = 16.dp)
                Line(Modifier.fillMaxWidth())
                SpacerHeight(height = 16.dp)
                Row(
                    Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column {
                        Text(
                            text = "FULL NAME", style = caption.copy(
                                color = GreyFlight
                            )
                        )
                        SpacerHeight(height = 5.dp)
                        data.forEach { dap ->
                            Text(
                                text = dap.firtName.plus(" ${dap.lastName}"), style = caption.copy()
                            )
                        }
                        SpacerHeight(height = 16.dp)
                        Text(
                            text = "DEPARTURE", style = caption.copy(
                                color = GreyFlight
                            )
                        )
                        SpacerHeight(height = 5.dp)
                        Text(
                            text = "10 Nov, 18.00", style = caption.copy()
                        )
                    }

                    Column {
                        Text(
                            text = "FLIGHT", style = caption.copy(
                                color = GreyFlight
                            )
                        )
                        SpacerHeight(height = 5.dp)
                        data.forEach { dap ->
                            Text(
                                text = "OKL018", style = caption.copy()
                            )
                        }
                        SpacerHeight(height = 16.dp)
                        Text(
                            text = "CLASS", style = caption.copy(
                                color = GreyFlight
                            )
                        )
                        SpacerHeight(height = 5.dp)
                        Text(
                            text = "Business", style = caption.copy()
                        )
                    }
                }
                SpacerHeight(height = 16.dp)
                Dotted(Modifier.fillMaxWidth())
                SpacerHeight(height = 16.dp)
                Text(
                    text = "SCAN THIS BARCODE", style = caption.copy()
                )
                SpacerHeight(height = 16.dp)
                Image(painter = painterResource(id = R.drawable.barcode), contentDescription = "")
            }
            Spacer(modifier = Modifier.weight(1f))
            ButtonNext(
                click = {
                    context.apply {
                        Intent(this, MainActivity::class.java).apply {
                            flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(this)
                            (context as AppCompatActivity).finish()
                        }
                    }
                }, title = "Return to Home", modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoardingPassPreview() {
    BoardingPassScreen()
}