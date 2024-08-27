package com.example.testapp.data.createuser

import javax.inject.Inject

data class ErrorCreateUser @Inject constructor(
    val error: String
)
