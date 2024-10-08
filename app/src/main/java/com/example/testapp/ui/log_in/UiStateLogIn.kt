package com.example.testapp.ui.log_in

import com.example.testapp.data.models.auth.UserModel

sealed class UiStateLogIn {
    data class Loading(val isLoading: Boolean): UiStateLogIn()
    data class Data(val data: UserModel): UiStateLogIn()
    data class Error(val message: String?): UiStateLogIn()
    data object None: UiStateLogIn()
}