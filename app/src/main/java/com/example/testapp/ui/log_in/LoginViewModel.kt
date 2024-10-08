package com.example.testapp.ui.log_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.models.auth.AuthModel
import com.example.testapp.data.database.entity.SaveUser
import com.example.testapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _state = MutableStateFlow<UiStateLogIn>(UiStateLogIn.None)
    val state: StateFlow<UiStateLogIn> get() = _state

    fun authorization(user: AuthModel) {
        viewModelScope.launch {
            repository.authorizationRepo(AuthModel(user.login, user.password))
                .onStart {
                    _state.value = UiStateLogIn.Loading(true)
                }
                .catch { e ->
                    _state.value = UiStateLogIn.Error(e.message)
                }
                .collect {result ->
                    _state.value = result
                }
        }
    }

    suspend fun getUserLoginAuth(login: String): SaveUser? {
        return repository.getUserByLoginAuth(login)
    }

    fun saveUserAuth(user: SaveUser) {
        viewModelScope.launch {
            repository.saveUserAuth(user)
        }
    }
}