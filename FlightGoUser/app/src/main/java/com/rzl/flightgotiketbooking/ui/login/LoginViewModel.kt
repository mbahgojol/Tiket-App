package com.rzl.flightgotiketbooking.ui.login

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
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val resultLogin = MutableLiveData<ResultState>()

    fun login(bodyAuth: BodyAuth) {
        viewModelScope.launch {
            repository.login(bodyAuth)
                .collect(resultLogin::setValue)
        }
    }
}