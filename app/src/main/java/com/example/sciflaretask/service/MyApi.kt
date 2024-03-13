package com.example.sciflaretask.service

import com.example.sciflaretask.model.MessageReq
import com.example.sciflaretask.model.MessageResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApi {
    @POST("SciMsg")
    suspend fun createMessage(@Body body: MessageReq): Response<MessageResp>

    @GET("SciMsg")
    suspend fun readAllMessage(): Response<List<MessageResp>>
}