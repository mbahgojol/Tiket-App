package com.rzl.flightgotiketbooking.data.network

import com.rzl.flightgotiketbooking.data.model.*
import com.rzl.flightgotiketbooking.data.model.login.LoginResponse
import com.rzl.flightgotiketbooking.data.model.register.BodyAuth
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("register-member")
    suspend fun register(@Body bodyRegister: BodyAuth): String

    @POST("login")
    suspend fun login(@Body body: BodyAuth): LoginResponse

    @GET("ticket")
    suspend fun listFlight(@Header("Authorization") authorization: String): ResponseFlightList

    @POST("ticket/transaction/{id}")
    suspend fun createTrx(
        @Header("Authorization") authorization: String, @Path("id") id: Int,
        @Body body: RequestBody
    ): ResponseCreateTrx

    @GET("ticket/transaction/data/{id}")
    suspend fun getDetailTrx(
        @Header("Authorization") authorization: String, @Path("id") id: Int
    ): ResponseDetailTrx

    @GET("ticket/wishlist/list")
    suspend fun listWishlist(@Header("Authorization") authorization: String): ResponseListWish

    @POST("ticket/wishlist/{id}")
    suspend fun addWish(
        @Header("Authorization") authorization: String, @Path("id") id: Int
    ): ResponseAddWish

    @GET("current-user")
    suspend fun getProfile(@Header("Authorization") authorization: String): ResponseProfile

    @GET("ticket/{id}")
    suspend fun getDetailTicket(
        @Header("Authorization") authorization: String, @Path("id") id: Int
    ): ResponseFlightList.ResponseFlightListItem
}