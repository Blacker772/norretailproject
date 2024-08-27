package com.example.testapp.ui.sign_up

import com.example.testapp.data.createuser.ErrorCreateUser

sealed class UiStateSignUp {
    data class Loading(val isLoading: Boolean): UiStateSignUp()
    data class Data(val data: ErrorCreateUser, val isLoading: Boolean): UiStateSignUp()
    data class Error(val message: String?, val isLoading: Boolean): UiStateSignUp()
    data object None: UiStateSignUp()
}