package binar.academy.flightgoadmin.ui.detailorder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.academy.flightgoadmin.data.Repository
import binar.academy.flightgoadmin.data.model.ResponseOrderDetail
import binar.academy.flightgoadmin.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailOrderViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val uistateList = MutableStateFlow<UiState<ResponseOrderDetail>>(UiState.Loading)

    fun getOrderById(id: Int) {
        viewModelScope.launch {
            repository.getOrderDetail(id)
                .collect(uistateList)
        }
    }
}