package binar.academy.flightgoadmin.data.model.booked


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("bukti_Pembayaran")
    val buktiPembayaran: String,
    @SerializedName("checkIn")
    val checkIn: Any,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("product")
    val product: Product,
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
) : java.io.Serializable