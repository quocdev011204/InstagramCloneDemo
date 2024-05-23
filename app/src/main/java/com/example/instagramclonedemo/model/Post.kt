package com.example.instagramclonedemo.model


data class Post(
    var profile: Int,
    val userName: String,
    val postImageList: List<Int>,
    val description: String,
    val likedBy: List<User>
)

data class User(
    var profile: Int,
    val userName: String,
    val storyCount: Int = 0,
    var stories: List<Int> = listOf()
)

