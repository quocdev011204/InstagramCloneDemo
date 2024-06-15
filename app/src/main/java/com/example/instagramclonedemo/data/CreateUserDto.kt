package com.example.instagramclonedemo.data

import com.example.instagramclonedemo.domain.model.MessageData
import com.example.instagramclonedemo.domain.model.StoryViewers
import com.example.instagramclonedemo.domain.model.UserPref
import java.util.Date

data class CreateUserDto(
    val userId: String? =  "",
    val username: String ?="",
    val password: String ?="",
    val email: String? ="",
    val fullName: String? ="",
    val imageUrl: String ?="",
    val createdDate: Date? = null,
    val chatList: List<String>? = emptyList(),
    val favorites: List<String>? = emptyList(),
    var blocked: List<String>? = emptyList(),
    var viewedStories: List<StoryViewers>? = emptyList(),
    var bio: String? = "",
    var latestMessage: MessageData? = null,
    var token : String? = "",
    var status: String? = "",
    var statusExpiry: Long? = 0,
    var online: Boolean? = false,
    var typing: String? = "",
    var userPref: UserPref? = UserPref()
)
