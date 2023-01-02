package binar.academy.flightgoadmin.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import binar.academy.flightgoadmin.data.database.DataStoreAdmin
import binar.academy.flightgoadmin.data.model.admin.AdminDataClass
import binar.academy.flightgoadmin.data.model.admin.AdminResponse
import binar.academy.flightgoadmin.data.model.tiket.ResponseMessage
import binar.academy.flightgoadmin.data.model.tiket.TiketResponse
import binar.academy.flightgoadmin.data.model.tiket.TiketResponseItem
import binar.academy.flightgoadmin.data.remote.APIService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(var api : APIService, @ApplicationContext appContext: Context): ViewModel() {

    // LivaData
    var livedataToken: MutableLiveData<String> = MutableLiveData()
    var livedataIsLogin: MutableLiveData<Boolean> = MutableLiveData()
    private val adminStore: DataStoreAdmin = DataStoreAdmin(appContext)
    val getAll : MutableLiveData<TiketResponse?> = MutableLiveData()
    var login : MutableLiveData<AdminResponse?> = MutableLiveData()
    var tiket : MutableLiveData<TiketResponseItem?> = MutableLiveData()
    var postTiket : MutableLiveData<TiketResponseItem?> = MutableLiveData()
    var delTiket : MutableLiveData<ResponseMessage?> = MutableLiveData()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getLiveAllTic()
        }
    }

    fun saveData(role: String, token_: String) {
        GlobalScope.launch {
            adminStore.saveData(role, token_)
        }
    }

    fun getToken()= adminStore.getToken().asLiveData()
    fun getRole()= adminStore.getRole().asLiveData()

    fun saveLoginStatus(status: Boolean) {
        GlobalScope.launch {
            adminStore.setLogin(status)
        }
    }

    fun removeLoginStatus() {
        GlobalScope.launch {
            adminStore.removeLogin()
        }
    }

    fun getLiveAllTic() : LiveData<TiketResponse?>{
        return getAll
    }

    fun LoginLive() : LiveData<AdminResponse?> {
        return login
    }

    fun tiketById() : MutableLiveData<TiketResponseItem?>{
        return tiket
    }

    fun delLiveData() : MutableLiveData<ResponseMessage?>{
        return delTiket
    }

    //Retrofit
    fun apiLogin(email: String, pass: String){
        api.adminLogin(AdminDataClass(email,pass))
            .enqueue(object : Callback<AdminResponse>{
                override fun onResponse(call: Call<AdminResponse>, response: Response<AdminResponse>) {
                    if (response.isSuccessful){
                        val body = response.body()
                        if (body!=null){
                            login.postValue(body)
                            Log.d("SUCCESS", "$body")
                        }else{
                            login.postValue(null)
                            error("NULL" + response.message())
                        }
                    }else{
                        login.postValue(null)
                        error(response.message())
                    }
                }

                override fun onFailure(call: Call<AdminResponse>, t: Throwable) {
                    login.postValue(null)
                    Log.e("FAILED", "SOMETHING WRONG", t )
                }

            })
    }

    fun getApiAllTic(){
        api.getAllTic()
            .enqueue(object : Callback<TiketResponse>{
                override fun onResponse(
                    call: Call<TiketResponse>,
                    response: Response<TiketResponse>,
                ) {
                    if (response.isSuccessful){
                        val body = response.body()
                        if (body != null){
                            getAll.postValue(body)
                            Log.d("SUCCESS GET : ", "$body")
                        }else{
                            getAll.postValue(null)
                            Log.d("FAILED GET", "onResponse: $body")
                        }
                    }
                }

                override fun onFailure(call: Call<TiketResponse>, t: Throwable) {
                    getAll.postValue(null)
                    Log.e("FAILED GET : ", "${t.message}", t)
                }

            })
    }

    fun postTiket(token_: String, request: ResponseMessage) {
        api.addTiket(token_, request)
            .enqueue(object : Callback<TiketResponseItem> {
                override fun onResponse(
                    call: Call<TiketResponseItem>,
                    response: Response<TiketResponseItem>
                ) {
                    val body = response.body()
                    if (response.isSuccessful){
                        postTiket.postValue(body)
                        Log.d("SUCCESSFULL CREATE DATA", "$body")
                    } else {
                        postTiket.postValue(null)
                        Log.d("FAILED CREATE DATA", "$body")
                    }
                }

                override fun onFailure(call: Call<TiketResponseItem>, t: Throwable) {
                    postTiket.postValue(null)
                }
            })
    }

    fun putTiketById(
        token_: String, id_tiket : String, bandara_asal: RequestBody, bandara_asal_: RequestBody, bandara_tujuan: RequestBody,
        bandara_tujuan_: RequestBody, bentuk_penerbangan : RequestBody, depature_date : RequestBody, depature_date_: RequestBody,
        depature_time : RequestBody, depature_time_: RequestBody, desctiption: RequestBody, image_product:  MultipartBody.Part,
        jenis_penerbangan : RequestBody, kode_negara_asal: RequestBody, kode_negara_asal_: RequestBody, kode_negara_tujuan: RequestBody,
        kode_negara_tujuan_: RequestBody, kota_asal: RequestBody, kota_asal_: RequestBody, kota_tujuan: RequestBody, kota_tujuan_: RequestBody,
        price: RequestBody, price_: RequestBody, total_price: RequestBody)
    {
        api.putTiket(
            token_, id_tiket, bandara_asal, bandara_asal_, bandara_tujuan, bandara_tujuan_, bentuk_penerbangan,
            depature_date, depature_date_, depature_time, depature_time_, desctiption, image_product,
            jenis_penerbangan, kode_negara_asal, kode_negara_asal_, kode_negara_tujuan, kode_negara_tujuan_,
            kota_asal, kota_asal_, kota_tujuan, kota_tujuan_, price, price_, total_price)
            .enqueue(object : Callback<TiketResponseItem>{
                override fun onResponse(
                    call: Call<TiketResponseItem>,
                    response: Response<TiketResponseItem>,
                ) {
                    val body = response.body()
                    if (response.isSuccessful){
                        tiket.postValue(body)
                        Log.d("SUCCESS UPDATE", "$body")
                    }else{
                        tiket.postValue(null)
                        Log.d("FAILED UPDATE", "$body")
                    }
                }

                override fun onFailure(call: Call<TiketResponseItem>, t: Throwable) {
                    tiket.postValue(null)
                    Log.e("FAILED PUT : ", "${t.message}", t)
                }

            })


    }

    fun deleteTiket(token_: String, id_tiket: String){
        api.delTiket(token_, id_tiket)
            .enqueue(object : Callback<ResponseMessage>{
                override fun onResponse(
                    call: Call<ResponseMessage>,
                    response: Response<ResponseMessage>,
                ) {
                    if (response.isSuccessful){
                        val body = response.body()
                        if (body!=null){
                            delTiket.postValue(body)
                        }else{
                            delTiket.postValue(null)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                    delTiket.postValue(null)
                    Log.e("FAILED PUT : ", "${t.message}", t)
                }

            })
    }

}