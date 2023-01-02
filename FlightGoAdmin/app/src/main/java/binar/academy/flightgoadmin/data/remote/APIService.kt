package binar.academy.flightgoadmin.data.remote

import binar.academy.flightgoadmin.data.model.admin.AdminDataClass
import binar.academy.flightgoadmin.data.model.admin.AdminResponse
import binar.academy.flightgoadmin.data.model.booked.BookingResponse
import binar.academy.flightgoadmin.data.model.tiket.ResponseMessage
import binar.academy.flightgoadmin.data.model.tiket.TiketResponse
import binar.academy.flightgoadmin.data.model.tiket.TiketResponseItem
import binar.academy.flightgoadmin.data.model.user.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    //get all tiket
    @GET("ticket")
    fun getAllTic() : Call<TiketResponse>

    @POST("login")
    fun adminLogin(@Body login: AdminDataClass): Call<AdminResponse>

    @GET("logout")
    fun adminLogout(@Header("Authorization") token: String): Call<String>

    @GET("users")
    fun getUsers(@Header("Authorization") token: String) : Call<UserResponse>

    @GET("ticket/transaction/data")
    fun getBookedHistory() : Call<BookingResponse>

    @POST("ticket")
    fun addTiket(
        @Header("Authorization") token: String,
        @Body request : ResponseMessage
    ) : Call<TiketResponseItem>

    @PUT("ticket/{id}")
    @Multipart
    fun putTiket(
        @Header("Authorization") token: String,
        @Path("id") id : String,
        @Part("bandara_asal") bandara_asal: RequestBody,
        @Part("bandara_asal_") bandara_asal_: RequestBody,
        @Part("bandara_tujuan") bandara_tujuan: RequestBody,
        @Part("bandara_tujuan_") bandara_tujuan_: RequestBody,
        @Part("bentuk_penerbangan") bentuk_penerbangan : RequestBody,
        @Part("depature_date") depature_date : RequestBody,
        @Part("depature_date_") depature_date_: RequestBody,
        @Part("depature_time") depature_time : RequestBody,
        @Part("depature_time_") depature_time_: RequestBody,
        @Part("desctiption") desctiption: RequestBody,
        @Part image_product:  MultipartBody.Part,
        @Part("jenis_penerbangan") jenis_penerbangan : RequestBody,
        @Part("kode_negara_asal") kode_negara_asal: RequestBody,
        @Part("kode_negara_asal_") kode_negara_asal_: RequestBody,
        @Part("kode_negara_tujuan") kode_negara_tujuan: RequestBody,
        @Part("kode_negara_tujuan_") kode_negara_tujuan_: RequestBody,
        @Part("kota_asal") kota_asal: RequestBody,
        @Part("kota_asal_") kota_asal_: RequestBody,
        @Part("kota_tujuan") kota_tujuan: RequestBody,
        @Part("kota_tujuan_") kota_tujuan_: RequestBody,
        @Part("price") price: RequestBody,
        @Part("price_") price_: RequestBody,
        @Part("total_price") total_price: RequestBody
    ) : Call<TiketResponseItem>

    @DELETE("ticket/{id}")
    fun delTiket(
        @Header("Authorization") token: String,
        @Path("id") id : String
    ): Call<ResponseMessage>
}