package com.example.testapp.data

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class MailModel @Inject constructor(
    val email: String
)
