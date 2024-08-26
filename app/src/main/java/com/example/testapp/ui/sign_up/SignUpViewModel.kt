package com.example.testapp.ui.sign_up

import androidx.lifecycle.ViewModel
import com.example.testapp.data.createuser.CreateUserModel
import com.example.testapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    suspend fun createUser(
        login: String, password: String, family: String,
        name: String, lastname: String, email: String
    ) {
        repository.createUSerRepo(
            CreateUserModel(
                login, password, family, name, lastname, email
            )
        )
    }
}