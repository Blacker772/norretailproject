package com.example.testapp.ui.menu.viewpager.pages.route.points

import javax.inject.Inject

data class Shop @Inject constructor(
    val icon: Int,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)
