package binar.academy.flightgoadmin.data.remote

import binar.academy.flightgoadmin.data.model.ResponseAccTrx
import binar.academy.flightgoadmin.data.model.ResponseTransaction
import binar.academy.flightgoadmin.data.model.admin.ResponseProfile
import binar.academy.flightgoadmin.data.model.tiket.TiketResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiAdminService {
    @GET("ticket")
    suspend fun listFlight(@Header("Authorization") authorization: String): TiketResponse

    @GET("current-user")
    suspend fun getProfile(@Header("Authorization") authorization: String): ResponseProfile

    @GET("ticket/transaction/data")
    suspend fun getTransaction(@Header("Authorization") authorization: String): ResponseTransaction

    @PUT("ticket/transaction/accept/{id}")
    suspend fun accTransaction(
        @Header("Authorization") authorization: String, @Path("id") id: Int
    ): ResponseAccTrx

    @PUT("ticket/transaction/reject/{id}")
    suspend fun rejectTransaction(
        @Header("Authorization") authorization: String, @Path("id") id: Int
    ): ResponseAccTrx
}