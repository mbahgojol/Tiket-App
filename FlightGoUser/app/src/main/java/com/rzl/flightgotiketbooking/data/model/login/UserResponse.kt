package com.rzl.flightgotiketbooking.data.model.login

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)
