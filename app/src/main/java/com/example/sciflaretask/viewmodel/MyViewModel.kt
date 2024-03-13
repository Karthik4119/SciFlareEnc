package com.example.sciflaretask.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sciflaretask.model.MessageReq
import com.example.sciflaretask.model.MessageResp
import com.example.sciflaretask.service.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: MyRepository
):ViewModel() {

    val sendMesage : MutableLiveData<Response<MessageResp>> = MutableLiveData()
    val readAllMsgList : MutableLiveData<Response<List<MessageResp>>> = MutableLiveData()

    fun createMessage(body:MessageReq){
        viewModelScope.launch {
            sendMesage.value = repository.createMessage(body)
        }
    }

    fun readAllMsg(){
        viewModelScope.launch {
            readAllMsgList.value = repository.readAllMessage()
        }
    }
}