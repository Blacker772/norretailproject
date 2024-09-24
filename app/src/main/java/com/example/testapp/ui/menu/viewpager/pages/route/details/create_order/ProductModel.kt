package com.example.testapp.ui.menu.viewpager.pages.route.details.create_order

import javax.inject.Inject

data class ProductModel @Inject constructor(
    val articles: Int,
    val name: String,
    val price: Double,
    val stock: Int
)