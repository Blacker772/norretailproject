package com.example.testapp.ui.recover.mail

import com.example.testapp.data.createuser.CreateUserModel

sealed class UiStateMail {
    data class Loading(val isLoading: Boolean): UiStateMail()
    data class Data(val data: CreateUserModel, val isLoading: Boolean): UiStateMail()
    data class Error(val message: String?, val isLoading: Boolean): UiStateMail()
    data object None: UiStateMail()
}