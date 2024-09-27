package com.example.testapp.data.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "clients")
data class Clients(
    @PrimaryKey val id: Int,

    @ColumnInfo
    @SerializedName("n_user")
    val nUser:String,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    @SerializedName("delivery_address")
    val deliveryAddress: String,

    @ColumnInfo
    @SerializedName("legal_address")
    val legalAddress: String,

    @ColumnInfo
    val inn: String,

    @ColumnInfo
    val kpp: String,

    @ColumnInfo
    @SerializedName("phone_number")
    val phoneNumber: String,

    @ColumnInfo
    val boss: String,

    @ColumnInfo
    @SerializedName("boss_phone_number")
    val bossNumber: String,

    @ColumnInfo
    val channel: Int = 0,

    @ColumnInfo
    @SerializedName("id_format_shop")
    val idFormatShop: Int = 0
):Parcelable