package binar.academy.flightgoadmin.ui.addtiket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.academy.flightgoadmin.data.Repository
import binar.academy.flightgoadmin.data.model.tiket.ResponseMessage
import binar.academy.flightgoadmin.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class AddTiketViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val stateStatus = MutableLiveData<UiState<ResponseMessage>>()

    fun addTiket(departureData: FormData, returData: FormData) {
        val imageFile = departureData.imageFile.value ?: File("")

        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("jenis_penerbangan", "Internasional")
            .addFormDataPart("bentuk_penerbangan", "round-trip")
            .addFormDataPart("kota_asal", "bandung")
            .addFormDataPart("bandara_asal", "gatau")
            .addFormDataPart("kota_tujuan", "singapore")
            .addFormDataPart("bandara_tujuan", "gatau")
            .addFormDataPart("depature_date", "12/30/2022")
            .addFormDataPart("depature_time", "13:00")
            .addFormDataPart("kode_negara_asal", "ID")
            .addFormDataPart("kode_negara_tujuan", "SG")
            .addFormDataPart("price", "750000")
            .addFormDataPart("kota_asal_", "Singapore")
            .addFormDataPart("bandara_asal_", "dd")
            .addFormDataPart("kota_tujuan_", "bandung")
            .addFormDataPart("bandara_tujuan_", "dddd")
            .addFormDataPart("depature_date_", "4/01/2023")
            .addFormDataPart("depature_time_", "05:00")
            .addFormDataPart("kode_negara_asal_", "SG")
            .addFormDataPart("kode_negara_tujuan_", "ID")
            .addFormDataPart("price_", "750000")
            .addFormDataPart("total_price", "750000")
            .addFormDataPart("desctiption", "bing chiling")
            .addFormDataPart(
                "image_product",
                "Image_${System.currentTimeMillis()}",
                RequestBody.create("application/octet".toMediaTypeOrNull(), departureData.imageFile.value!!.readBytes())
            ).build()

        viewModelScope.launch {
            repository.createTiket(requestBody)
                .collect(stateStatus::setValue)
        }
    }
}