package com.example.instagramclonedemo.domain.use_cases

import com.example.instagramclonedemo.domain.model.LastMessageModel
import com.example.instagramclonedemo.domain.responsitory.MessageRepository
import com.example.instagramclonedemo.util.Resource
import javax.inject.Inject

class GetLastMessagesUseCase @Inject constructor(private val messageRepository: MessageRepository) {



    operator fun invoke(myId:String,fireBaseResponse: (Resource<List<LastMessageModel>>) -> Unit) {

        messageRepository.getLastMessages(myId) {
            fireBaseResponse(it)
        }

    }

}