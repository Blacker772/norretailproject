package com.example.testapp.ui.menu.viewpager.pages.route

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.R
import com.example.testapp.data.models.pages.ButtonModel
import com.example.testapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    //Список клиентов
    private val _state = MutableStateFlow<UiStateRoute>(UiStateRoute.None)
    val state: StateFlow<UiStateRoute> get() = _state

    private fun insertClients() {
        viewModelScope.launch {
            repository.insertClientsInDBRepo()
        }
    }

    fun getClientDB() {
        viewModelScope.launch {
            repository.getClientsDBRepo()
                .onStart {
                    _state.value = UiStateRoute.Loading(true)
                }
                .catch {
                    _state.value = UiStateRoute.Error(it.message)
                }
                .collect {
                    if (it.isNotEmpty()){
                        _state.value = UiStateRoute.Data(it)
                    }else{
                        insertClients()
                        _state.value = UiStateRoute.Data(it)
                    }
                }
        }
    }

    //Поиск клиентов
    private val _search = MutableStateFlow("")
    val search: StateFlow<String> get() = _search

    //Установка поискового запроса
    fun setSearchQuery(query: String) {
        _search.value = query
    }

    //Список кнопок меню сбоку
    fun listOfButton() = listOf(
        ButtonModel(R.drawable.ic_acc, "Мой Профиль"),
        ButtonModel(R.drawable.ic_download, "Загрузить данные"),
        ButtonModel(R.drawable.ic_upload, "Выгрузить данные"),
        ButtonModel(R.drawable.ic_manual, "Справочники"),
        ButtonModel(R.drawable.ic_setting1, "Настройки")
    )
}


