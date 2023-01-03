package binar.academy.flightgoadmin.ui.addtiket

import android.Manifest
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.findNavController
import binar.academy.flightgoadmin.R
import binar.academy.flightgoadmin.databinding.FragmentAddTiketBinding
import binar.academy.flightgoadmin.ui.component.*
import binar.academy.flightgoadmin.utils.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.rzl.flightgotiketbooking.ui.component.Container
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream


class FormData {
    var jenisPenerbangan = mutableStateOf("")
    var bentukPenerbangan = mutableStateOf("")
    var kotaAsal = mutableStateOf("")
    var bandaraAsal = mutableStateOf("")
    var kotaTujuan = mutableStateOf("")
    var badaraTujuan = mutableStateOf("")
    var kodeNegaraAsal = mutableStateOf("")
    var kodeNegaraTujuan = mutableStateOf("")
    var departureDate = mutableStateOf("")
    var departureTime = mutableStateOf("")
    var price = mutableStateOf("")
    var image = mutableStateOf("")
    var imageFile = mutableStateOf<InputStream?>(null)
    var description = mutableStateOf("")
}

@Composable
fun remeberFormData() = remember {
    FormData()
}

@AndroidEntryPoint
class AddTiketFragment : Fragment() {

    private var binding: FragmentAddTiketBinding? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAddTiketBinding.inflate(inflater, container, false)
        binding?.composeView?.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AddTiketScreen(back = {
                    findNavController().navigateUp()
                })
            }
        }
        return binding?.root
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddTiketScreen(
    back: () -> Unit = {}, viewModel: AddTiketViewModel = hiltViewModel()
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Card(
                Modifier.size(200.dp), shape = RoundedCornerShape(10.dp)
            ) {
                viewModel.stateStatus.observeAsState().value?.let {
                    when (it) {
                        is UiState.Error -> {
                            Log.e("Error", it.errorMessage)
                            ErrorMessage(
                                msg = it.errorMessage, modifier = Modifier
                                    .padding(16.dp)
                                    .size(200.dp)
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
                                    text = it.data.message,
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

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_MEDIA_IMAGES,
        )
    )

    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    PermissionsRequired(multiplePermissionsState = permissionState,
        permissionsNotGrantedContent = { },
        permissionsNotAvailableContent = { }) {}

    val formDataDepature = remeberFormData()
    val formDataReturn = remeberFormData()

    val jenisP = listOf("Domestic", "International")
    val bentukP = listOf("Oneway", "Roundtrip")
    var selectedJ by remember {
        mutableStateOf(jenisP[0])
    }

    formDataDepature.jenisPenerbangan.value = selectedJ
    var selectedB by remember {
        mutableStateOf(bentukP[0])
    }
    formDataDepature.bentukPenerbangan.value = selectedB

    val showReturnFlight by remember {
        derivedStateOf { (selectedB == bentukP[1]) }
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Tambah Tiket", style = largeTitle.copy(
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
                .padding(16.dp)
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Depature Flight", style = caption)
                SpacerHeight(height = 16.dp)
                Card(
                    Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), elevation = 2.dp
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Jenis Penerbangan", style = caption.copy(
                                    fontSize = 12.sp
                                )
                            )
                            jenisP.forEach {
                                RadioText(
                                    Modifier.selectable(selected = (selectedJ == it), onClick = {
                                        selectedJ = it
                                    }), title = it, selected = (selectedJ == it)
                                )
                            }
                        }
                        SpacerHeight(height = 10.dp)
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Bentuk Penerbangan", style = caption.copy(
                                    fontSize = 12.sp
                                )
                            )
                            bentukP.forEach {
                                RadioText(
                                    Modifier.selectable(selected = (selectedB == it), onClick = {
                                        selectedB = it
                                    }), title = it, selected = (selectedB == it)
                                )
                            }
                        }
                        FormAddTiket(formDataDepature)
                    }
                }

                AnimatedVisibility(visible = showReturnFlight) {
                    Column(
                        Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SpacerHeight(height = 16.dp)
                        Text(text = "Return Flight", style = caption)
                        SpacerHeight(height = 16.dp)
                        Card(
                            Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            elevation = 2.dp
                        ) {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                FormAddTiket(formDataReturn)
                            }
                        }
                    }
                }
                SpacerHeight(height = 16.dp)
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = back,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = GreyFlight, contentColor = Color.White
                    )
                ) {
                    Text(text = "Cancel")
                }
                Button(
                    onClick = {
                        showDialog = true
                        viewModel.addTiket(formDataDepature, formDataReturn)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = OrangeFlight, contentColor = Color.White
                    )
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}

@Composable
fun RadioText(
    modifier: Modifier = Modifier, selected: Boolean = false, title: String = "Label"
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selected, onClick = null, colors = RadioButtonDefaults.colors(
                selectedColor = GreyFlight
            )
        )
        SpacerWidth(width = 8.dp)
        Text(
            text = title, style = caption.copy(
                fontSize = 10.sp, color = GreyFlight
            )
        )
    }
}

@Composable
fun FormAddTiket(formData: FormData) {
    val context = LocalContext.current
    val pickImgGallery = rememberPickImageGallery()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        pickImgGallery.imageUri = uri
    }

    pickImgGallery.imageUri?.let {
        val stream = context.contentResolver.openInputStream(it)
        formData.image.value = it.toString()
        formData.imageFile.value = stream
        pickImgGallery.bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, it)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            ImageDecoder.decodeBitmap(source)
        }
    }

    val listFormData = mutableListOf(
        formData.kotaAsal,
        formData.bandaraAsal,
        formData.kotaTujuan,
        formData.badaraTujuan,
        formData.kodeNegaraAsal,
        formData.kodeNegaraTujuan,
        formData.departureDate,
        formData.departureTime,
        formData.price,
        formData.image,
        formData.description,
    )

    val listForm = mutableListOf(
        "Kota asal",
        "Bandara asal",
        "Kota tujuan",
        "Bandara tujuan",
        "Kode negara asal",
        "Kode negara tujuan",
        "Departure date",
        "Departure time",
        "Price",
        "Image",
        "Description",
    )

    Column(Modifier.fillMaxWidth()) {
        (0..3).forEach { i ->
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MyTextField(
                    listForm[i.plus(i)], state = listFormData[i.plus(i)], Modifier.weight(1f)
                )
                MyTextField(
                    listForm[i.plus(1)], state = listFormData[i.plus(1)], Modifier.weight(1f)
                )
            }
            SpacerHeight(height = 16.dp)
        }
        MyTextField(
            listForm[8], state = listFormData[8], Modifier.fillMaxWidth()
        )
        SpacerHeight(height = 16.dp)
        AnimatedVisibility(visible = (pickImgGallery.bitmap != null)) {
            Column(
                Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                pickImgGallery.bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "",
                        modifier = Modifier.size(100.dp)
                    )
                }
                SpacerHeight(height = 16.dp)
            }
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyTextField(
                listForm[9], state = listFormData[9], Modifier.weight(1f)
            )
            Image(painter = painterResource(id = R.drawable.ic_baseline_add_photo_alternate_24),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        launcher.launch("image/*")
                    })
        }
        SpacerHeight(height = 16.dp)
        MyTextField(
            listForm[10], state = listFormData[10], Modifier.fillMaxWidth()
        )
        SpacerHeight(height = 16.dp)
    }
}


@Composable
fun MyTextField(
    label: String = "Label", state: MutableState<String>, modifier: Modifier = Modifier
) {
    OutlinedTextField(modifier = modifier, value = state.value, onValueChange = {
        state.value = it
    }, label = {
        Text(text = label, style = caption)
    }, keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next))
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun AddTiketScreenPreview() {
    MaterialTheme {
        AddTiketScreen()
    }
}