package binar.academy.flightgoadmin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.academy.flightgoadmin.data.model.booked.BookingResponse
import binar.academy.flightgoadmin.data.model.booked.Data
import binar.academy.flightgoadmin.data.remote.APIService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BookedViewModel @Inject constructor(private var api : APIService) : ViewModel() {

    private var livedataBooked : MutableLiveData<BookingResponse?> = MutableLiveData()
    private var booked : MutableLiveData<Data?> = MutableLiveData()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            gotldBookedHistory()
        }
    }

    fun gotldBookedHistory() : LiveData<BookingResponse?> {
        return livedataBooked
    }

    fun getAPIBooked() {
        api.getBookedHistory().enqueue(object : Callback<BookingResponse>{
            override fun onResponse(
                call: Call<BookingResponse>,
                response: Response<BookingResponse>,
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body!=null){
                        livedataBooked.postValue(body)
                        Log.d("SUCCESS", "$body")
                    }else{
                        livedataBooked.postValue(null)
                        error("NULL" + response.message())
                    }
                }else{
                    livedataBooked.postValue(null)
                    error(response.message())
                }
            }

            override fun onFailure(call: Call<BookingResponse>, t: Throwable) {
                livedataBooked.postValue(null)
                Log.e("FAILED", "SOMETHING WRONG", t )
            }
        })
    }
}