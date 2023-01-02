package com.rzl.flightgotiketbooking.data.model.register


import com.google.gson.annotations.SerializedName

data class BodyAuth(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)