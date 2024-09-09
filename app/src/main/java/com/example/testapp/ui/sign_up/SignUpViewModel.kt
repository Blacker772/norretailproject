package com.example.testapp.ui.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.createuser.CreateUserModel
import com.example.testapp.data.createuser.ErrorCreateUser
import com.example.testapp.data.database.entity.Users
import com.example.testapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _state = MutableStateFlow<UiStateSignUp>(UiStateSignUp.None)
    val state: StateFlow<UiStateSignUp> get() = _state

    fun createUser(
        login: String, password: String, family: String,
        name: String, lastname: String, email: String,
    ) {
        viewModelScope.launch {
            repository.createUSerRepo(
                CreateUserModel(
                    login, password, family, name, lastname, email
                )
            )
                .onStart {
                    _state.value = UiStateSignUp.Loading(true)
                }
                .catch { e ->
                    _state.value = UiStateSignUp.Error(e.message, false)
                }
                .collect { result ->
                    _state.value = UiStateSignUp.Data(result, false)
                }
        }
    }
    fun insertUser(
        login: String, password: String, family: String,
        name: String, lastname: String, email: String,
    ) {
        viewModelScope.launch {
            repository.insertUser(
                Users(null, login, password, family, name, lastname, email)
            )
        }
    }
}