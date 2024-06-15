package com.example.instagramclonedemo.model

import com.example.instagramclonedemo.data.CreateUserDto


data class Post(
    val userId: String? = null,
    val content: String? = "",
    val mediaUrl: String? = null,
    val timestamp: Long = 0,
    val profile: String ="", // Giá trị mặc định của profile
    val username: String = "", // Giá trị mặc định của userName
    val postImageList: List<Int> = emptyList(), // Giá trị mặc định của postImageList
    val likedBy: List<CreateUserDto> = emptyList() // Giá trị mặc định của likedBy
)

//data class User(
//    var profile: Int,
//    val userName: String,
//    val storyCount: Int = 0,
//    var stories: List<Int> = listOf()
//)

