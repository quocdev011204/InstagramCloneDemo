package com.example.instagramclonedemo.util

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedRef {
    fun storeData(
        fullName: String,
        email: String,
        userName: String,
        imageUrl: String,
        context: Context
    ) {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("fullName", fullName)
        editor.putString("email", email)
        editor.putString("userName", userName)
        editor.putString("imageUrl", imageUrl)
        editor.apply()
    }

    fun getUserName(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("userName", "")!!
    }
    fun getEmail(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("email", "")!!
    }
    fun getImage(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("imageUrl", "")!!
    }

}
