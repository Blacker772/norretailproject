package com.example.testapp.ui.menu.pages.route.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import javax.inject.Inject
@Parcelize
class RouteModel @Inject constructor(
    val icon: Int,
    val code: String,
    val name: String,
    val address: String,
    val price: String,
    val sale: String,
    val day: String,
    val time: String
): Parcelable