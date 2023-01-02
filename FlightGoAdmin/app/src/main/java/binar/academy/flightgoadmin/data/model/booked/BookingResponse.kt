package binar.academy.flightgoadmin.data.model.booked


import com.google.gson.annotations.SerializedName

data class BookingResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("status")
    val status: Int
)