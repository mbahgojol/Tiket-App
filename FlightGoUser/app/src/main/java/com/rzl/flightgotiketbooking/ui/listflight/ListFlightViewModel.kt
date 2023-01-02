package com.rzl.flightgotiketbooking.ui.listflight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzl.flightgotiketbooking.data.Repository
import com.rzl.flightgotiketbooking.data.model.ResponseFlightList
import com.rzl.flightgotiketbooking.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListFlightViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val uistateList = MutableStateFlow<UiState<ResponseFlightList>>(UiState.Loading)

    fun getListFlight() {
        viewModelScope.launch {
            repository.listFlight()
                .collect(uistateList)
        }
    }
}