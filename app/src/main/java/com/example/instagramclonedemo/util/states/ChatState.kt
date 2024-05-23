package com.example.instagramclonedemo.util.states

import com.example.instagramclonedemo.domain.model.ChatModel

data class ChatState (
    val isLoading: Boolean = false,
    val messages: List<ChatModel> = mutableListOf(),
    val error: String = ""
)