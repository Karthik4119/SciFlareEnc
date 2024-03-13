package com.example.sciflaretask.service

import android.util.Log
import com.example.sciflaretask.model.MessageReq
import com.example.sciflaretask.model.MessageResp
import retrofit2.Response

class MyRepositoryImplementation(
    private val api : MyApi
): MyRepository {
    override suspend fun createMessage(body: MessageReq) : Response<MessageResp> {
        return api.createMessage(body)
    }

    override suspend fun readAllMessage() : Response<List<MessageResp>> {
        return api.readAllMessage()
    }
}