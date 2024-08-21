package com.example.testapp.data.channels

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class ChannelsModel @Inject constructor(
    val id: Int,
    val name: String,
    @SerializedName("short_name")
    val shortName: String,
    val description: String
)