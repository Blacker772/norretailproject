package com.example.testapp.ui.menu.viewpager

import androidx.lifecycle.ViewModel
import com.example.testapp.R
import com.example.testapp.data.pages.ButtonModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VPViewModel @Inject constructor(
//    private val apiService: ApiService
) : ViewModel() {

//    private val _liveData = MutableLiveData<List<ChannelsModel>>()
//    val liveData: LiveData<List<ChannelsModel>> get() = _liveData
//
//    suspend fun getChannels() {
//        val result = apiService.getChannels()
//        if (result.isSuccessful) {
//            _liveData.postValue(result.body())
//        }
//    }

    fun listOfButton() = listOf(
        ButtonModel(R.drawable.ic_acc, "Мой Профиль"),
        ButtonModel(R.drawable.ic_download, "Загрузить данные"),
        ButtonModel(R.drawable.ic_upload, "Выгрузить данные"),
        ButtonModel(R.drawable.ic_manual, "Справочники"),
        ButtonModel(R.drawable.ic_setting1, "Настройки")
    )
}