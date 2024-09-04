package com.example.testapp.ui.recover.mail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.recover.MailModel
import com.example.testapp.data.createuser.CreateUserModel
import com.example.testapp.data.response.ApiService
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MailViewModel @Inject constructor(
    private val apiService: ApiService
): ViewModel() {

    private val _state = MutableLiveData<CreateUserModel>()
    val state: LiveData<CreateUserModel> get() = _state

    fun checkMail(email: String){
        viewModelScope.launch {
            val result = apiService.checkMail(mail = email)
            if (result.isSuccessful){
                _state.postValue(result.body())
            }
        }
    }
}