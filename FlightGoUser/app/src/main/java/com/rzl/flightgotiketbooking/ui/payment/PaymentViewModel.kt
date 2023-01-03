package com.rzl.flightgotiketbooking.ui.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzl.flightgotiketbooking.data.Repository
import com.rzl.flightgotiketbooking.data.model.ResponseAddWish
import com.rzl.flightgotiketbooking.data.model.ResponseCreateTrx
import com.rzl.flightgotiketbooking.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import javax.inject.Inject


@HiltViewModel
class PaymentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val state = MutableLiveData<UiState<ResponseAddWish>>()
    val stateCreateTrx = MutableLiveData<UiState<ResponseCreateTrx>>()

    fun addWish(id: Int) {
        viewModelScope.launch {
            repository.addWish(id).collect {
                state.value = it
            }
        }
    }

    fun createTrx(id: Int, inputStream: InputStream) {
        val imgBody = inputStream.readBytes().toRequestBody(
            contentType = "application/octet-stream".toMediaTypeOrNull()
        )

        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart(
                "bukti_Pembayaran",
                "Image_${System.currentTimeMillis()}",
                imgBody
            ).build()

        viewModelScope.launch {
            repository.createTrx(id, requestBody)
                .collect {
                    stateCreateTrx.value = it
                }
        }
    }
}
