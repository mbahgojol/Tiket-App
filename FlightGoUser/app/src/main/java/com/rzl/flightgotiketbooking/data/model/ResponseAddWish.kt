package com.rzl.flightgotiketbooking.data.model


import com.google.gson.annotations.SerializedName

data class ResponseAddWish(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
) {
    data class Data(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("productId")
        val productId: String,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("userId")
        val userId: Int
    )
}