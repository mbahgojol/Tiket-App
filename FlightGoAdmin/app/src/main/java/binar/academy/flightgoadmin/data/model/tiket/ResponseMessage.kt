package binar.academy.flightgoadmin.data.model.tiket


import com.google.gson.annotations.SerializedName

data class ResponseMessage(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)