package com.example.testapp.ui

import com.example.testapp.data.auth.UserModel

sealed class UiState {
    data class Loading(val isLoading: Boolean): UiState()
    data class Data(val data: UserModel, val isLoading: Boolean): UiState()
    data class Error(val message: String?, val isLoading: Boolean): UiState()
    data object None: UiState()
}