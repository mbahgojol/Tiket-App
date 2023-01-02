package com.rzl.flightgotiketbooking.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzl.flightgotiketbooking.data.model.register.BodyAuth
import com.rzl.flightgotiketbooking.data.Repository
import com.rzl.flightgotiketbooking.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val resultStateRegister = MutableLiveData<ResultState>()

    fun register(bodyRegister: BodyAuth) {
        viewModelScope.launch {
            repository.register(bodyRegister).collect(resultStateRegister::setValue)
        }
    }
}