package binar.academy.flightgoadmin.ui.detailtiket

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.findNavController
import binar.academy.flightgoadmin.R
import binar.academy.flightgoadmin.data.model.tiket.TiketResponseItem
import binar.academy.flightgoadmin.databinding.FragmentDetailTiketBinding
import binar.academy.flightgoadmin.ui.addtiket.*
import binar.academy.flightgoadmin.ui.component.CenterProgressBar
import binar.academy.flightgoadmin.ui.component.ErrorMessage
import binar.academy.flightgoadmin.ui.component.SpacerHeight
import binar.academy.flightgoadmin.utils.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.rzl.flightgotiketbooking.ui.component.Container
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTiketFragment : Fragment() {
    private var binding: FragmentDetailTiketBinding? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailTiketBinding.inflate(inflater, container, false)
        val getBun = arguments?.getSerializable("dataTiket") as TiketResponseItem
        binding?.composeView?.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                DetailTiketScreen(
                    back = {
                        findNavController().navigateUp()
                    }, tiket = getBun
                )
            }
        }
        return binding?.root
    }
}

@Composable
fun UpdateTiketDialog(
    dialogState: MutableState<Boolean> = mutableStateOf(false), back: () -> Unit
) {
    Dialog(onDismissRequest = {
        dialogState.value = false
        back()
    }) {
        Card(
            Modifier.size(200.dp), shape = RoundedCornerShape(10.dp)
        ) {
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
                    text = "Berhasil Update Tiket", style = caption.copy(
                        fontWeight = FontWeight.Normal, textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DetailTiketScreen(
    back: () -> Unit = {},
    viewModel: DetailTiketViewModel = hiltViewModel(),
    tiket: TiketResponseItem? = null
) {
    val showSuccessDialog = remember {
        mutableStateOf(false)
    }

    if (showSuccessDialog.value) {
        UpdateTiketDialog(dialogState = showSuccessDialog, back)
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    if (showDialog) {
        Dialog(onDismissRequest = {
            showDialog = false
            back()
        }) {
            Card(
                Modifier.size(200.dp), shape = RoundedCornerShape(10.dp)
            ) {
                viewModel.stateStatus.observeAsState().value?.let {
                    when (it) {
                        is UiState.Error -> {
                            Log.e("Error", it.errorMessage)
                            ErrorMessage(
                                msg = it.errorMessage,
                                modifier = Modifier
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

    val formDataDepature = remeberFormData()
    setDataTiket(formDataDepature, tiket)
    val formDataReturn = remeberFormData()
    setDataTiketReturn(formDataReturn, tiket)

    val jenisP = listOf("Domestik", "Internasional")
    val bentukP = listOf("One-way", "Round-trip")
    val indexJ =
        if (formDataDepature.jenisPenerbangan.value == "") 0 else jenisP.indexOf(formDataDepature.jenisPenerbangan.value)
    val indexB =
        if (formDataDepature.bentukPenerbangan.value == "") 0 else jenisP.indexOf(formDataDepature.bentukPenerbangan.value)

    var selectedJ by remember {
        mutableStateOf(jenisP[indexJ])
    }
    formDataDepature.jenisPenerbangan.value = selectedJ

    var selectedB by remember {
        mutableStateOf(bentukP[indexB])
    }
    formDataDepature.bentukPenerbangan.value = selectedB

    val showReturnFlight by remember {
        derivedStateOf { (selectedB == bentukP[1]) }
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Detail Tiket", style = largeTitle.copy(
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
                    onClick = {
                        viewModel.updateTiket(formDataDepature, formDataReturn)
                        showSuccessDialog.value = true
                    },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = GreyFlight, contentColor = Color.White
                    )
                ) {
                    Text(text = "Update")
                }
                Button(
                    onClick = {
                        showDialog = true
                        tiket?.let { viewModel.deleteTicket(it.id) }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = OrangeFlight, contentColor = Color.White
                    )
                ) {
                    Text(text = "Delete")
                }
            }
        }
    }
}

fun setDataTiket(formDataDepature: FormData, tiket: TiketResponseItem?) {
    formDataDepature.apply {
        tiket?.let {
            kotaAsal.value = it.kotaAsal
            bandaraAsal.value = it.bandaraAsal
            kotaTujuan.value = it.kotaTujuan
            badaraTujuan.value = it.bandaraTujuan
            kodeNegaraAsal.value = it.kodeNegaraAsal
            kodeNegaraTujuan.value = it.kodeNegaraTujuan
            departureDate.value = it.depatureDate
            departureTime.value = it.depatureTime
            price.value = it.price.toString()
            image.value = it.imageProduct
            description.value = it.desctiption
        }
    }
}

fun setDataTiketReturn(formDataDepature: FormData, tiket: TiketResponseItem?) {
    formDataDepature.apply {
        tiket?.let {
            kotaAsal.value = it.kotaAsal_return
            bandaraAsal.value = it.bandaraAsal_return
            kotaTujuan.value = it.kotaTujuan_return
            badaraTujuan.value = it.bandaraTujuan_return
            kodeNegaraAsal.value = it.kodeNegaraAsal_return
            kodeNegaraTujuan.value = it.kodeNegaraTujuan_return
            departureDate.value = it.depatureDate_return
            departureTime.value = it.depatureTime_return
            price.value = it.price_return.toString()
            image.value = it.imageProduct
            description.value = it.desctiption
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPrev() {
    MaterialTheme {
        DetailTiketScreen()
    }
}