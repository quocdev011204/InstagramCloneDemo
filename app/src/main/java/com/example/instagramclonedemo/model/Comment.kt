package com.example.instagramclonedemo.model

data class Comment(
    val userId: String = "",
    val username: String = "",
    val content: String = "",
    val timestamp: Long = 0
)