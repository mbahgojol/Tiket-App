package com.rzl.flightgotiketbooking.ui.notif

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzl.flightgotiketbooking.data.Repository
import com.rzl.flightgotiketbooking.data.model.ResponseDetailTrx
import com.rzl.flightgotiketbooking.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val state = MutableStateFlow<UiState<ResponseDetailTrx>>(UiState.Loading)

    fun getNotif() {
        viewModelScope.launch {
            repository.getDetailTrx(1)
                .collect(state)
        }
    }
}