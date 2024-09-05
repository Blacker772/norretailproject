package com.example.testapp.ui.recover.mail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.recover.MailModel
import com.example.testapp.data.createuser.CreateUserModel
import com.example.testapp.data.response.ApiService
import com.example.testapp.repository.Repository
import com.example.testapp.ui.log_in.UiStateLogIn
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MailViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _state = MutableStateFlow<UiStateMail>(UiStateMail.None)
    val state: StateFlow<UiStateMail> get() = _state

    fun checkMail(mail: String){
        viewModelScope.launch {
            repository.cheMail(mail)
                .onStart {
                    _state.value = UiStateMail.Loading(true)
                }
                .catch { e ->
                    _state.value = UiStateMail.Error(e.message, false)
                }
                .collect{
                    _state.value = UiStateMail.Data(it, false)
                }
        }
    }
}