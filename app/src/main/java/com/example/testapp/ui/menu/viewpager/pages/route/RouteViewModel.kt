package com.example.testapp.ui.menu.viewpager.pages.route

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
class RouteViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _state = MutableStateFlow<UiStateRoute>(UiStateRoute.None)
    val state: StateFlow<UiStateRoute> get() = _state


    fun getClients(){
        viewModelScope.launch {
            repository.getClientsRepo()
                .onStart {
                    _state.value = UiStateRoute.Loading(true)
                }
                .catch {e ->
                    _state.value = UiStateRoute.Error(e.message)
                }
                .collect{ result ->
                    _state.value = result
                }
        }
    }
}