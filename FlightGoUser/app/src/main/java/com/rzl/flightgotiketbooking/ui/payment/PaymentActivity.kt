package com.rzl.flightgotiketbooking.ui.payment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.rzl.flightgotiketbooking.R
import com.rzl.flightgotiketbooking.ui.boardingpass.BoardingPassActivity
import com.rzl.flightgotiketbooking.ui.component.CenterProgressBar
import com.rzl.flightgotiketbooking.ui.component.ErrorMessage
import com.rzl.flightgotiketbooking.ui.component.SpacerHeight
import com.rzl.flightgotiketbooking.ui.component.SpacerWidth
import com.rzl.flightgotiketbooking.ui.detailtiket.ButtonNext
import com.rzl.flightgotiketbooking.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getIntExtra("id", 0)
        val price = intent.getIntExtra("price", 0)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    PaymentScreen(id, price = price)
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

class ResultImage {
    var imageUri by mutableStateOf<Uri?>(null)
    var bitmap by mutableStateOf<Bitmap?>(null)
}

@Composable
fun rememberPickImageGallery() = remember {
    ResultImage()
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PaymentScreen(
    id: Int = 0,
    price: Int = 0,
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val permissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES,
            )
        )
    } else {
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
        )
    }

    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    PermissionsRequired(multiplePermissionsState = permissionState,
        permissionsNotGrantedContent = { },
        permissionsNotAvailableContent = { }) {}

    val context = LocalContext.current
    val showSuccessDialog = remember {
        mutableStateOf(false)
    }

    val showDialogAddWish = remember {
        mutableStateOf(false)
    }

    if (showDialogAddWish.value) {
        viewModel.addWish(id = id)
        AddWishDialog(dialogState = showDialogAddWish, viewModel)
    }

    if (showSuccessDialog.value) {
        PaymentSuccessDialog(dialogState = showSuccessDialog)
    }

    val pickImgGallery = rememberPickImageGallery()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        pickImgGallery.imageUri = uri
    }

    pickImgGallery.imageUri?.let {
        pickImgGallery.bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, it)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            ImageDecoder.decodeBitmap(source)
        }
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
            Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            Text(
                text = "Total Biaya", style = largeTitleSemiBold.copy(
                    fontSize = 14.sp
                )
            )
            Text(
                text = price.toString().toCurrencyFormat(), style = largeTitleSemiBold.copy(
                    fontSize = 24.sp, color = OrangeFlight
                )
            )
            Text(
                text = "Pembayaran dapat dilakukan melalui transfer\n" + "ke nomor rekening 422231232 (Bank Kai) a/n FlightGo",
                style = caption
            )
            SpacerHeight(height = 30.dp)
            AnimatedVisibility(visible = (pickImgGallery.bitmap != null)) {
                Column(
                    Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    pickImgGallery.bitmap?.let { img ->
                        Image(
                            bitmap = img.asImageBitmap(),
                            contentDescription = "",
                            modifier = Modifier.size(100.dp)
                        )
                    }
                    SpacerHeight(height = 16.dp)
                }
            }
            Button(
                onClick = {
                    launcher.launch("image/*")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = GreyCard, contentColor = Color.White
                )
            ) {
                Text(
                    text = "Upload Bukti Pembayaran", style = caption.copy(
                        fontSize = 16.sp, color = Color.Black
                    )
                )
            }
            SpacerHeight(height = 30.dp)
            Button(
                onClick = {
                    showDialogAddWish.value = true
                },
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = RedFlight, contentColor = Color.White
                )
            ) {
                Text(
                    text = "Add to Wishlist", style = caption.copy(
                        fontSize = 16.sp, color = Color.White
                    )
                )
            }
            SpacerHeight(height = 16.dp)
            ButtonNext(Modifier.fillMaxWidth(), title = "Continue", click = {
                pickImgGallery.imageUri?.let { uri ->
                    context.contentResolver.openInputStream(uri)
                        ?.let { it1 -> viewModel.createTrx(id, it1) }
                }
                showSuccessDialog.value = true
            })
        }
    }
}

@Composable
fun AddWishDialog(
    dialogState: MutableState<Boolean> = mutableStateOf(false), viewModel: PaymentViewModel
) {
    Dialog(onDismissRequest = {
        dialogState.value = false
    }) {
        Card(
            Modifier.size(200.dp), shape = RoundedCornerShape(10.dp)
        ) {
            viewModel.state.observeAsState().value?.let {
                when (it) {
                    is UiState.Error -> {
                        ErrorMessage(
                            msg = it.errorMessage, modifier = Modifier.padding(16.dp)
                        )
                    }
                    is UiState.Success -> {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(20.dp))
                                .background(color = Color.White),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.success_icon),
                                contentDescription = "",
                                Modifier.size(100.dp)
                            )
                            SpacerHeight(height = 20.dp)
                            Text(
                                text = it.data.message, style = caption.copy(
                                    fontWeight = FontWeight.Normal, textAlign = TextAlign.Center
                                )
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