package com.example.testapp.data.models.createuser

import javax.inject.Inject

data class ErrorCreateUser @Inject constructor(
    val error: String
)
