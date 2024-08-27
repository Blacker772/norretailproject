package com.example.testapp.ui.log_in

import com.example.testapp.data.auth.UserModel

sealed class UiStateLogIn {
    data class Loading(val isLoading: Boolean): UiStateLogIn()
    data class Data(val data: UserModel, val isLoading: Boolean): UiStateLogIn()
    data class Error(val message: String?, val isLoading: Boolean): UiStateLogIn()
    data object None: UiStateLogIn()
}