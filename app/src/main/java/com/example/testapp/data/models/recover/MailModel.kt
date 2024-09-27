package com.example.testapp.data.models.recover

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class MailModel @Inject constructor(
    @SerializedName("email")
    val mail: String
)
