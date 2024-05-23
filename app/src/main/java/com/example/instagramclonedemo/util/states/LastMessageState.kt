package com.example.instagramclonedemo.util.states

import com.example.instagramclonedemo.domain.model.LastMessageModel

data class LastMessageState (
    val isLoading: Boolean = false,
    val messages: List<LastMessageModel> = mutableListOf(),
    val error: String = ""
)