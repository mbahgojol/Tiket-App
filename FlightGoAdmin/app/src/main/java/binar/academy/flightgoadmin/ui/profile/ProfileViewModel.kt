package binar.academy.flightgoadmin.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.academy.flightgoadmin.data.Repository
import binar.academy.flightgoadmin.data.database.DataStoreAdmin
import binar.academy.flightgoadmin.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository, private val pref: DataStoreAdmin
) : ViewModel() {

    val resultState = MutableLiveData<ResultState>()

    fun getProfile() {
        viewModelScope.launch {
            repository.getProfile().collect(resultState::setValue)
        }
    }

    fun logout() {
        viewModelScope.launch {
            pref.saveData(
                "", ""
            )
        }
    }
}