package binar.academy.flightgoadmin.data.model.tiket


import com.google.gson.annotations.SerializedName

data class TiketResponseItem(
    @SerializedName("bandara_asal")
    val bandaraAsal: String,
    @SerializedName("bandara_asal_")
    val bandaraAsal_return: String,
    @SerializedName("bandara_tujuan")
    val bandaraTujuan: String,
    @SerializedName("bandara_tujuan_")
    val bandaraTujuan_return: String,
    @SerializedName("bentuk_penerbangan")
    val bentukPenerbangan: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("depature_date")
    val depatureDate: String,
    @SerializedName("depature_date_")
    val depatureDate_return: String,
    @SerializedName("depature_time")
    val depatureTime: String,
    @SerializedName("depature_time_")
    val depatureTime_return: String,
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
    val kodeNegaraAsal_return: String,
    @SerializedName("kode_negara_tujuan")
    val kodeNegaraTujuan: String,
    @SerializedName("kode_negara_tujuan_")
    val kodeNegaraTujuan_return: String,
    @SerializedName("kota_asal")
    val kotaAsal: String,
    @SerializedName("kota_asal_")
    val kotaAsal_return: String,
    @SerializedName("kota_tujuan")
    val kotaTujuan: String,
    @SerializedName("kota_tujuan_")
    val kotaTujuan_return: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("price_")
    val price_return: Int,
    @SerializedName("total_price")
    val totalPrice: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
) : java.io.Serializable