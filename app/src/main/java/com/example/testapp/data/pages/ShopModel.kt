package com.example.testapp.data.pages

import javax.inject.Inject

data class ShopModel @Inject constructor(
    val icon: Int,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)
