package com.rzl.flightgotiketbooking.data.model.login


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
) {
    data class Data(
        @SerializedName("accessToken")
        val accessToken: String,
        @SerializedName("address")
        val address: Any,
        @SerializedName("email")
        val email: String,
        @SerializedName("phone")
        val phone: Any,
        @SerializedName("refreshToken")
        val refreshToken: String,
        @SerializedName("role")
        val role: String,
        @SerializedName("userId")
        val userId: Int
    )
}