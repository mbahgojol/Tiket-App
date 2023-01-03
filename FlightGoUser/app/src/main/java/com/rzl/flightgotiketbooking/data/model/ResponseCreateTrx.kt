package com.rzl.flightgotiketbooking.data.model


import com.google.gson.annotations.SerializedName

data class ResponseCreateTrx(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
) {
    data class Data(
        @SerializedName("bukti_Pembayaran")
        val buktiPembayaran: String,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("productId")
        val productId: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("userIzin")
        val userIzin: Any,
        @SerializedName("userPassport")
        val userPassport: Any,
        @SerializedName("userVisa")
        val userVisa: Any
    )
}