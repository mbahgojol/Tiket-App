package binar.academy.flightgoadmin.ui.customers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.academy.flightgoadmin.data.Repository
import binar.academy.flightgoadmin.data.model.ResponseAccTrx
import binar.academy.flightgoadmin.data.model.ResponseTransaction
import binar.academy.flightgoadmin.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val uistateList = MutableStateFlow<UiState<ResponseTransaction>>(UiState.Loading)
    val stateStatusTrx = MutableLiveData<UiState<ResponseAccTrx>>()

    fun getListTransaction() {
        viewModelScope.launch {
            repository.getTransaction().collect(uistateList)
        }
    }

    fun rejectTrx(id: Int) {
        viewModelScope.launch {
            repository.rejectTransaction(id).collect(stateStatusTrx::setValue)
        }
    }

    fun accTrx(id: Int) {
        viewModelScope.launch {
            repository.accTransaction(id).collect(stateStatusTrx::setValue)
        }
    }
}