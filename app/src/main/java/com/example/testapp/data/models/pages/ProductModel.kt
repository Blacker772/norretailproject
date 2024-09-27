package com.example.testapp.data.models.pages

import javax.inject.Inject

data class ProductModel @Inject constructor(
    val articles: Int,
    val name: String,
    val price: Double,
    val stock: Int
)