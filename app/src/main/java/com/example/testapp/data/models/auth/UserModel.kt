package com.example.testapp.data.models.auth

import javax.inject.Inject

data class UserModel @Inject constructor(
    val error: String?,
    val status: String,
    val token: String?
)
