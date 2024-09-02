package com.example.testapp.ui.recover.mail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.data.recover.MailModel
import com.example.testapp.data.createuser.CreateUserModel
import com.example.testapp.data.response.ApiService
import javax.inject.Inject

class MailViewModel @Inject constructor(
    private val apiService: ApiService
): ViewModel() {

    private val _state = MutableLiveData<CreateUserModel>()
    val state: LiveData<CreateUserModel> get() = _state

    suspend fun checkMail(mail: String){
        val result = apiService.checkMail(MailModel(mail))
        if (result.isSuccessful){
            _state.postValue(result.body()) ?: throw Exception("Response is null")
        } else {
            throw Exception("Error")
        }
    }
}