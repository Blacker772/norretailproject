package com.example.testapp.ui.log_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.auth.AuthModel
import com.example.testapp.data.auth.UserModel
import com.example.testapp.data.response.ApiService
import com.example.testapp.repository.Repository
import com.example.testapp.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _state = MutableStateFlow<UiState>(UiState.None)
    val state: StateFlow<UiState> get() = _state

    fun getLogin(login: String, password: String){
        viewModelScope.launch {
            repository.getLoginRepo(login, password)
                .onStart {
                    _state.value = UiState.Loading(true)
                }
                .catch { e ->
                    _state.value = UiState.Error(e.message, false)
                }
                .collect{
                    _state.value = UiState.Data(it, false)
                }
        }
    }
}