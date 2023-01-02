package com.rzl.flightgotiketbooking.ui.notif

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.rzl.flightgotiketbooking.utils.largeTitle
import com.rzl.flightgotiketbooking.utils.largeTitleSemiBold
import com.rzl.flightgotiketbooking.utils.poppinsFamily


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
                    color = Color.White
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
                    elevation = 5.dp, shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
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