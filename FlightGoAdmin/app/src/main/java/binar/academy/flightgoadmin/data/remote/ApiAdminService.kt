package binar.academy.flightgoadmin.data.remote

import binar.academy.flightgoadmin.data.model.ResponseAccTrx
import binar.academy.flightgoadmin.data.model.ResponseOrderDetail
import binar.academy.flightgoadmin.data.model.ResponseTransaction
import binar.academy.flightgoadmin.data.model.admin.ResponseProfile
import binar.academy.flightgoadmin.data.model.tiket.ResponseMessage
import binar.academy.flightgoadmin.data.model.tiket.TiketResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiAdminService {
    @GET("ticket")
    suspend fun listFlight(@Header("Authorization") authorization: String): TiketResponse

    @GET("current-user")
    suspend fun getProfile(@Header("Authorization") authorization: String): ResponseProfile

    @GET("ticket/transaction/data")
    suspend fun getTransaction(@Header("Authorization") authorization: String): ResponseTransaction

    @GET("ticket/transaction/data/{id}")
    suspend fun getOrderDetail(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ): ResponseOrderDetail

    @PUT("ticket/transaction/accept/{id}")
    suspend fun accTransaction(
        @Header("Authorization") authorization: String, @Path("id") id: Int
    ): ResponseAccTrx

    @POST("ticket")
    suspend fun createTiket(
        @Header("Authorization") authorization: String,
        @Body body: RequestBody
    ): ResponseMessage

    @PUT("ticket/transaction/reject/{id}")
    suspend fun rejectTransaction(
        @Header("Authorization") authorization: String, @Path("id") id: Int
    ): ResponseAccTrx
}