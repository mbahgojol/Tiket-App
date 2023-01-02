package binar.academy.flightgoadmin.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.academy.flightgoadmin.data.Repository
import binar.academy.flightgoadmin.data.model.tiket.TiketResponse
import binar.academy.flightgoadmin.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val uistateList = MutableStateFlow<UiState<TiketResponse>>(UiState.Loading)

    fun getListFlight() {
        viewModelScope.launch {
            repository.listFlight()
                .collect(uistateList)
        }
    }
}