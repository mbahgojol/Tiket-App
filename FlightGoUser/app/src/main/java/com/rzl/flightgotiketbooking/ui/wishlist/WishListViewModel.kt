package com.rzl.flightgotiketbooking.ui.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzl.flightgotiketbooking.data.Repository
import com.rzl.flightgotiketbooking.data.model.ResponseListWish
import com.rzl.flightgotiketbooking.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val stateList = MutableStateFlow<UiState<ResponseListWish>>(UiState.Loading)

    fun getListWish() {
        viewModelScope.launch {
            repository.listWishlist()
                .collect(stateList)
        }
    }
}