package com.rzl.flightgotiketbooking.ui.payment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.rzl.flightgotiketbooking.R
import com.rzl.flightgotiketbooking.data.model.Ewallet
import com.rzl.flightgotiketbooking.ui.boardingpass.BoardingPassActivity
import com.rzl.flightgotiketbooking.ui.component.SpacerHeight
import com.rzl.flightgotiketbooking.ui.component.SpacerWidth
import com.rzl.flightgotiketbooking.ui.detailtiket.ButtonNext
import com.rzl.flightgotiketbooking.utils.GreyCard
import com.rzl.flightgotiketbooking.utils.caption
import com.rzl.flightgotiketbooking.utils.largeTitle
import com.rzl.flightgotiketbooking.utils.largeTitleSemiBold

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    PaymentScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        PaymentScreen()
    }
}

@Composable
fun PaymentScreen() {
    val context = LocalContext.current
    val showSuccessDialog = remember {
        mutableStateOf(false)
    }

    if (showSuccessDialog.value) {
        PaymentSuccessDialog(dialogState = showSuccessDialog)
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Payment", style = largeTitle.copy(
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
            Modifier.padding(it)
        ) {
            val ewallets = listOf(
                Ewallet("Google Pay", painterResource(id = R.drawable.google_icon)),
                Ewallet("Paypal", painterResource(id = R.drawable.paypal)),
                Ewallet("Apple Pay", painterResource(id = R.drawable.apple)),
            )
            var selectEwallet by remember {
                mutableStateOf("")
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            text = "Payment Methods", style = largeTitleSemiBold.copy(
                                fontSize = 14.sp
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "Add New Card", style = largeTitleSemiBold.copy(
                            fontSize = 14.sp, color = colorResource(id = R.color.orange)
                        ), modifier = Modifier.clickable {

                        })
                    }
                }

                items(ewallets.size, key = {
                    ewallets[it].nama
                }) {
                    ItemEwallet(value = selectEwallet,
                        title = ewallets[it].nama,
                        painter = ewallets[it].image,
                        modifier = Modifier.selectable((selectEwallet == ewallets[it].nama)) {
                            selectEwallet = ewallets[it].nama
                        })
                }
            }
            SpacerHeight(height = 16.dp)
            ButtonNext(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp), title = "Continue", click = {
                    showSuccessDialog.value = true
                })
        }
    }
}

@Composable
fun PaymentSuccessDialog(
    context: Context = LocalContext.current,
    dialogState: MutableState<Boolean> = mutableStateOf(false)
) {
    Dialog(onDismissRequest = {
        dialogState.value = false
    }) {
        Column(
            Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(color = Color.White)
                .padding(horizontal = 30.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.success_icon), contentDescription = "")
            SpacerHeight(height = 20.dp)
            Text(
                text = "Payment Successfull!", style = largeTitleSemiBold.copy(
                    color = colorResource(id = R.color.orange)
                )
            )
            Text(
                text = "Your trip tickets has been booked successfully.", style = caption.copy(
                    fontWeight = FontWeight.Normal, textAlign = TextAlign.Center
                )
            )
            SpacerHeight(height = 10.dp)
            ButtonNext(Modifier.fillMaxWidth(), title = "View Ticket", click = {
                dialogState.value = false
                context.apply {
                    Intent(this, BoardingPassActivity::class.java).apply {
                        startActivity(this)
                    }
                }
            })
            SpacerHeight(height = 8.dp)
            NegativeButton(Modifier.fillMaxWidth(), title = "Cancel", click = {
                dialogState.value = false
            })
        }
    }
}

@Composable
fun NegativeButton(
    modifier: Modifier = Modifier, click: () -> Unit = {}, title: String = ""
) {
    Button(
        onClick = click,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = GreyCard, contentColor = colorResource(id = R.color.orange)
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
fun DialogPreview() {
    PaymentSuccessDialog()
}

@Composable
fun ItemEwallet(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.google_icon),
    title: String = "Google Pay",
    value: String = ""
) {
    Card(modifier = modifier, shape = RoundedCornerShape(10.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(18.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painter, contentDescription = "")
            SpacerWidth(width = 10.dp)
            Text(text = title, style = largeTitleSemiBold)
            Spacer(modifier = Modifier.weight(1f))
            RadioButton(
                selected = (value == title),
                onClick = null,
                colors = RadioButtonDefaults.colors(selectedColor = colorResource(id = R.color.orange))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    ItemEwallet()
}