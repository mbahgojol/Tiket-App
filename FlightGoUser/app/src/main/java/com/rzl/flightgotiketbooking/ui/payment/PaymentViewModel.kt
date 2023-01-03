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
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
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

    fun createTrx(id: Int, path: String) {
        val imageFile = File(path)
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart(
                "bukti_Pembayaran",
                "Image_${System.currentTimeMillis()}",
                imageFile.asRequestBody("image/*".toMediaType())
            ).build()

        viewModelScope.launch {
            repository.createTrx(id, requestBody)
                .collect {
                    stateCreateTrx.value = it
                }
        }
    }
}
