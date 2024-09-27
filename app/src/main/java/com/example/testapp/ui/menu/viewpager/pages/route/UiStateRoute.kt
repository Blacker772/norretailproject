package com.example.testapp.ui.menu.viewpager.pages.route

import com.example.testapp.data.database.entity.Clients

sealed class UiStateRoute {
    data class Loading(val isLoading: Boolean): UiStateRoute()
    data class Data(val data: List<Clients>): UiStateRoute()
    data class Error(val message: String?): UiStateRoute()
    data object None: UiStateRoute()
}