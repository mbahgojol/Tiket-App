package binar.academy.flightgoadmin.data.model.admin


import com.google.gson.annotations.SerializedName

data class ResponseProfile(
    @SerializedName("address")
    val address: Any,
    @SerializedName("email")
    val email: String,
    @SerializedName("image_user")
    val imageUser: String,
    @SerializedName("izin")
    val izin: Any,
    @SerializedName("name")
    val name: Any,
    @SerializedName("passport")
    val passport: Any,
    @SerializedName("phone")
    val phone: Any,
    @SerializedName("role")
    val role: String,
    @SerializedName("visa")
    val visa: Any
)