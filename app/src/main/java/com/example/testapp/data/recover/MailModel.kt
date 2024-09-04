package com.example.testapp.data.recover

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class MailModel @Inject constructor(
    @SerializedName("email")
    val mail: String
)
