package com.rzl.flightgotiketbooking.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseProfile(
    @SerializedName("email") val email: String = "",
    @SerializedName("image_user") val imageUser: String = "",
    @SerializedName("role") val role: String = "",
) : Parcelable