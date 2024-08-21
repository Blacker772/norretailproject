package com.example.testapp.ui.sign_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.data.createuser.CreateUserModel
import com.example.testapp.data.response.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    val liveData = MutableLiveData<CreateUserModel>()

    suspend fun createUser(
        login: String,
        password: String,
        family: String,
        name: String,
        lastname: String,
        email: String
    ) {
        val result = apiService.createUser(
            CreateUserModel(
            login = login,
            password = password,
            family = family,
            name = name,
            lastname = lastname,
            email = email
        )
        )

        if (result.isSuccessful){
            liveData.postValue(result.body())
        }
    }

}