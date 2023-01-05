package com.rzl.flightgotiketbooking.ui.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzl.flightgotiketbooking.data.Repository
import com.rzl.flightgotiketbooking.data.local.SharedPref
import com.rzl.flightgotiketbooking.data.model.ResponseProfile
import com.rzl.flightgotiketbooking.utils.ResultState
import com.rzl.flightgotiketbooking.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository, private val pref: SharedPref
) : ViewModel() {

    val resultState = MutableLiveData<ResultState>()
    val state = MutableStateFlow<UiState<ResponseProfile>>(UiState.Loading)
    val clickImg = MutableStateFlow(false)
    val bitmap = MutableLiveData<Uri>()

    fun getProfile() {
        viewModelScope.launch {
            repository.getProfile().collect(resultState::setValue)
        }
    }

    fun getProfile1() {
        viewModelScope.launch {
            repository.getProfile1().collect(state)
        }
    }

    fun logout() {
        viewModelScope.launch {
            pref.saveToken("")
        }
    }
}