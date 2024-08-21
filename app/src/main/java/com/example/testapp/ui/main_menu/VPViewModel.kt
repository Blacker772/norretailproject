package com.example.testapp.ui.main_menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.data.channels.ChannelsModel
import com.example.testapp.data.response.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VPViewModel @Inject constructor(
    private val apiService: ApiService
): ViewModel() {

    private val _liveData = MutableLiveData<List<ChannelsModel>>()
    val liveData: LiveData<List<ChannelsModel>> get() = _liveData

    suspend fun getChannels(){
        val result = apiService.getChannels()
        if (result.isSuccessful){
            _liveData.postValue(result.body())
        }
    }
}