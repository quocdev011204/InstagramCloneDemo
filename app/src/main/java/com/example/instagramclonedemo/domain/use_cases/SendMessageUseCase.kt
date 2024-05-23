package com.example.instagramclonedemo.domain.use_cases

import com.example.instagramclonedemo.domain.responsitory.MessageRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val messageRepository: MessageRepository) {


    operator fun invoke(
        chatId: String,
        messageSenderId: String?,
        messageReceiverId: String?,
        messageSenderName: String?,
        messageReceiverName: String?,
        messageText: String?
    ) =
        messageRepository.sendMessage(
            chatId,
            messageSenderId,
            messageReceiverId,
            messageSenderName,
            messageReceiverName,
            messageText
        )


}