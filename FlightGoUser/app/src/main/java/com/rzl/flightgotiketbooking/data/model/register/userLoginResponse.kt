package com.rzl.flightgotiketbooking.data.model.register


import com.google.gson.annotations.SerializedName

data class userLoginResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)