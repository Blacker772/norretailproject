package com.example.testapp.ui.menu.viewpager.pages.route

import com.example.testapp.data.pages.ClientModel

sealed class UiStateRoute {
    data class Loading(val isLoading: Boolean): UiStateRoute()
    data class Data(val data: List<ClientModel>): UiStateRoute()
    data class Error(val message: String?): UiStateRoute()
    data object None: UiStateRoute()
}