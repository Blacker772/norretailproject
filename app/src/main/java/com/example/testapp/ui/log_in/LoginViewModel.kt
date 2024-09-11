package com.example.testapp.ui.log_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.auth.AuthModel
import com.example.testapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow<UiStateLogIn>(UiStateLogIn.None)
    val state: StateFlow<UiStateLogIn> get() = _state

    fun getLogin(user: AuthModel) {
        viewModelScope.launch {
            repository.getLoginRepo(AuthModel(user.login, user.password))
                .onStart {
                    _state.value = UiStateLogIn.Loading(true)
                    delay(2000)
                }
                .catch { e ->
                    _state.value = UiStateLogIn.Error(e.message, false)
                }
                .collect {
                    _state.value = UiStateLogIn.Data(it, false)
                }
        }
    }
}