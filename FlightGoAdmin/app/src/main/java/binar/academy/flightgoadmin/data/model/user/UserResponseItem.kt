package binar.academy.flightgoadmin.data.model.user


import com.google.gson.annotations.SerializedName

data class UserResponseItem(
    @SerializedName("address")
    val address: Any,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
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
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("visa")
    val visa: Any
)