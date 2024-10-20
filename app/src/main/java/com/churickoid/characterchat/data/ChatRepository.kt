package com.churickoid.characterchat.data

import com.churickoid.characterchat.domain.entity.Message
import com.churickoid.characterchat.domain.entity.MessageOwner
import retrofit2.awaitResponse

class ChatRepository(private val service: ChatApiService) {

    suspend fun getAnswerFromBot(text: String): Message{
        val answer = service.sendMessage(text).awaitResponse()
        return Message(answer.body().toString(), MessageOwner.SENDER)
    }
}