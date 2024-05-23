package com.example.instagramclonedemo.domain.model

data class User(
    var name :String = "",
    var userName :String = "",
    var userId :String = "",
    var email :String = "",
    var password :String = "",
    var imageUrl :String = "",
    var following : List<String> =  emptyList(),
    var followers : List<String> =  emptyList(),
    var totalPosts : String = "",
    var bio : String = "",
    var url : String = "",
    ){
    fun toMap(): Map<String, String?> = mapOf(
        "userId" to userId,
        "name" to name,
        "email" to email,
        "password" to password,
        "imageurl" to imageUrl
    )
}
