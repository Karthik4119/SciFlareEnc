package com.example.sciflaretask.service

import com.example.sciflaretask.model.MessageReq
import com.example.sciflaretask.model.MessageResp
import retrofit2.Response

interface MyRepository {
    suspend fun createMessage(body:MessageReq) : Response<MessageResp>
    suspend fun readAllMessage() : Response<List<MessageResp>>
}