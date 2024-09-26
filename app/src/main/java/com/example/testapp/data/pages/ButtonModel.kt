package com.example.testapp.data.pages

import javax.inject.Inject

data class ButtonModel @Inject constructor(
    val image: Int,
    val text: String
)
