package com.example.testapp.data.pages

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class ClientModel(
    val id: Int,
    @SerializedName("n_user")
    val nUser:String,
    val name: String,
    @SerializedName("delivery_address")
    val deliveryAddress: String,
    @SerializedName("legal_address")
    val legalAddress: String,
    val inn: String,
    val kpp: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val boss: String,
    @SerializedName("boss_phone_number")
    val bossNumber: String,
    val channel: Int = 0,
    @SerializedName("id_format_shop")
    val idFormatShop: Int = 0
):Parcelable
