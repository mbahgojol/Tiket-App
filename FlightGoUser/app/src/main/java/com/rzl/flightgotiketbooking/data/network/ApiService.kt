package com.rzl.flightgotiketbooking.data.network

import com.rzl.flightgotiketbooking.data.model.ResponseFlightList
import com.rzl.flightgotiketbooking.data.model.ResponseProfile
import com.rzl.flightgotiketbooking.data.model.login.LoginResponse
import com.rzl.flightgotiketbooking.data.model.register.BodyAuth
import retrofit2.http.*

interface ApiService {

    @POST("register-member")
    suspend fun register(@Body bodyRegister: BodyAuth): String

    @POST("login")
    suspend fun login(@Body body: BodyAuth): LoginResponse

    @GET("ticket")
    suspend fun listFlight(@Header("Authorization") authorization: String): ResponseFlightList

    @GET("current-user")
    suspend fun getProfile(@Header("Authorization") authorization: String): ResponseProfile

    @GET("ticket/{id}")
    suspend fun getDetailTicket(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ): ResponseFlightList.ResponseFlightListItem
}