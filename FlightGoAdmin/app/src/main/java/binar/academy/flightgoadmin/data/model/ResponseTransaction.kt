package binar.academy.flightgoadmin.data.model


import com.google.gson.annotations.SerializedName

data class ResponseTransaction(
    @SerializedName("data")
    val `data`: List<Data>,
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
        val userIzin: String,
        @SerializedName("userPassport")
        val userPassport: String,
        @SerializedName("userVisa")
        val userVisa: String
    ) {
        data class Product(
            @SerializedName("bandara_asal")
            val bandaraAsal: String,
            @SerializedName("bandara_asal_")
            val bandaraAsal_: String,
            @SerializedName("bandara_tujuan")
            val bandaraTujuan: String,
            @SerializedName("bandara_tujuan_")
            val bandaraTujuan_: String,
            @SerializedName("bentuk_penerbangan")
            val bentukPenerbangan: String,
            @SerializedName("createdAt")
            val createdAt: String,
            @SerializedName("depature_date")
            val depatureDate: String,
            @SerializedName("depature_date_")
            val depatureDate_: String,
            @SerializedName("depature_time")
            val depatureTime: String,
            @SerializedName("depature_time_")
            val depatureTime_: String,
            @SerializedName("desctiption")
            val desctiption: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("image_product")
            val imageProduct: String,
            @SerializedName("image_product_id")
            val imageProductId: String,
            @SerializedName("jenis_penerbangan")
            val jenisPenerbangan: String,
            @SerializedName("kode_negara_asal")
            val kodeNegaraAsal: String,
            @SerializedName("kode_negara_asal_")
            val kodeNegaraAsal_: String,
            @SerializedName("kode_negara_tujuan")
            val kodeNegaraTujuan: String,
            @SerializedName("kode_negara_tujuan_")
            val kodeNegaraTujuan_: String,
            @SerializedName("kota_asal")
            val kotaAsal: String,
            @SerializedName("kota_asal_")
            val kotaAsal_: String,
            @SerializedName("kota_tujuan")
            val kotaTujuan: String,
            @SerializedName("kota_tujuan_")
            val kotaTujuan_: String,
            @SerializedName("price")
            val price: Int,
            @SerializedName("price_")
            val price_: Int,
            @SerializedName("total_price")
            val totalPrice: Int,
            @SerializedName("updatedAt")
            val updatedAt: String
        )
    }
}