package com.example.instagramclonedemo.model

import com.example.instagramclonedemo.data.CreateUserDto


data class Post(
    val postId: String = "",
    val userId: String? = null,
    val content: String? = "",
    val mediaUrl: String? = null,
    val timestamp: Long = 0,
    val profile: String ="",
    val username: String = "",
    val postImageList: List<Int> = emptyList(),
    val likedBy: List<CreateUserDto> = emptyList(),
    val comments: List<Comment> = emptyList(),
    val likesCount: Long = 0L

)

//data class User(
//    var profile: Int,
//    val userName: String,
//    val storyCount: Int = 0,
//    var stories: List<Int> = listOf()
//)

