package com.example.testapp.data.pages

import javax.inject.Inject

data class ProductModel @Inject constructor(
    val articles: Int,
    val name: String,
    val price: Double,
    val stock: Int
)