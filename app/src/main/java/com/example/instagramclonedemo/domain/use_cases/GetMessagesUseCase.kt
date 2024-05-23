package com.example.instagramclonedemo.domain.use_cases

import com.example.instagramclonedemo.domain.model.ChatModel
import com.example.instagramclonedemo.domain.responsitory.MessageRepository
import com.example.instagramclonedemo.util.Resource
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(private val messageRepository: MessageRepository) {



    operator fun invoke(chatId:String,fireBaseResponse: (Resource<List<ChatModel>>) -> Unit) {

        messageRepository.getMessages(chatId) {
            fireBaseResponse(it)
        }

    }

}