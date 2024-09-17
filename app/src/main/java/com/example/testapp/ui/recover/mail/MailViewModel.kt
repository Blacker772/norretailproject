package com.example.testapp.ui.recover.mail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.repository.Repository
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
            repository.checkMailRepo(mail)
                .onStart {
                    _state.value = UiStateMail.Loading(true)
                }
                .catch { e ->
                    _state.value = UiStateMail.Error(e.message)
                }
                .collect{
                    _state.value = UiStateMail.Data(it)
                }
        }
    }
}