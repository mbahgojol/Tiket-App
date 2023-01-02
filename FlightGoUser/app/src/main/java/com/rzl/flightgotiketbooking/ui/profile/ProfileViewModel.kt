package com.rzl.flightgotiketbooking.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzl.flightgotiketbooking.data.Repository
import com.rzl.flightgotiketbooking.data.local.SharedPref
import com.rzl.flightgotiketbooking.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository,
    private val pref: SharedPref
) : ViewModel() {

    val resultState = MutableLiveData<ResultState>()

    fun getProfile() {
        viewModelScope.launch {
            repository.getProfile()
                .collect(resultState::setValue)
        }
    }

    fun logout() {
        viewModelScope.launch {
            pref.saveToken("")
        }
    }
}