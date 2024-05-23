package com.example.instagramclonedemo.domain.use_cases

import com.example.instagramclonedemo.domain.responsitory.MessageRepository
import javax.inject.Inject

class SendFileUseCase @Inject constructor(private val messageRepository: MessageRepository) {


    operator fun invoke(
        chatId: String,
        messageSenderId: String?,
        messageReceiverId: String?,
        messageSenderName: String? ,
        messageReceiverName: String? ,
        fileType: String,
        fileUri: Any,
    ) =
        messageRepository.uploadFile(
            chatId,
            messageSenderId,
            messageReceiverId,
            messageSenderName,
            messageReceiverName,
            fileType,
            fileUri,
        )


}