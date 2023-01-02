package com.rzl.flightgotiketbooking.data.model.register


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("userId")
    val userId: Int
)