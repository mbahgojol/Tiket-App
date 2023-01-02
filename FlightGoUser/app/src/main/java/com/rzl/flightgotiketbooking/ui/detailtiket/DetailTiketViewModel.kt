package com.rzl.flightgotiketbooking.ui.detailtiket

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
class DetailTiketViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val uistate =
        MutableStateFlow<UiState<ResponseFlightList.ResponseFlightListItem>>(UiState.Loading)

    fun getDetail(id: Int) {
        viewModelScope.launch {
            repository.getDetailTicket(id).collect(uistate)
        }
    }
}