package binar.academy.flightgoadmin.data.model


import com.google.gson.annotations.SerializedName

data class ResponseAccTrx(
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
        @SerializedName("checkIn")
        val checkIn: String,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("productId")
        val productId: Int,
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