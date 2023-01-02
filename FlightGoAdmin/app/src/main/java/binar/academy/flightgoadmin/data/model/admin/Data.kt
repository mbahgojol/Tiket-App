package binar.academy.flightgoadmin.data.model.admin


import com.google.gson.annotations.SerializedName

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