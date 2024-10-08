package com.example.testapp.data.models.auth

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class AuthModel @Inject constructor(
    @SerializedName("email")
    val login: String,
    val password: String
)
