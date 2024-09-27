package com.example.testapp.data.models.createuser

import javax.inject.Inject

data class CreateUserModel @Inject constructor(
    val login: String,
    val password: String,
    val family: String,
    val name: String,
    val lastname: String,
    val email: String,
    val blocked: Int = 0
)
