package binar.academy.flightgoadmin.ui.detailtiket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.academy.flightgoadmin.data.Repository
import binar.academy.flightgoadmin.data.model.tiket.ResponseMessage
import binar.academy.flightgoadmin.data.model.tiket.TiketResponseItem
import binar.academy.flightgoadmin.ui.addtiket.FormData
import binar.academy.flightgoadmin.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class DetailTiketViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val uistate = MutableStateFlow<UiState<TiketResponseItem>>(UiState.Loading)
    val stateStatus = MutableLiveData<UiState<ResponseMessage>>()

    fun getDetailTicket(id: Int) {
        viewModelScope.launch {
            repository.getTicket(id).collect(uistate)
        }
    }

    fun deleteTicket(id: Int) {
        viewModelScope.launch {
            repository.deleteTicket(id).collect {
                stateStatus.value = it
            }
        }
    }

    fun updateTiket(departureData: FormData, returData: FormData) {
        val imgBody = departureData.imageFile.value?.readBytes()?.toRequestBody(
            contentType = "image/*".toMediaTypeOrNull()
        )

        val requestBody = imgBody?.let {
            MultipartBody.Builder().setType(MultipartBody.FORM)
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
                    imgBody
                ).build()
        }

        viewModelScope.launch {
            requestBody?.let {
                repository.createTiket(it)
                    .collect(stateStatus::setValue)
            }
        }
    }
}