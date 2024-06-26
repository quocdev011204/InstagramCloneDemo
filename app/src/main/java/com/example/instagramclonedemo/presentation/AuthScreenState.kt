package com.example.instagramclonedemo.presentation

import android.net.Uri
import com.example.instagramclonedemo.data.CreateUserDto

data class AuthScreenState(
    val isLoading: Boolean = false
)

sealed class AuthScreenEvents {
    data class OnLogin(val email: String, val password: String) : AuthScreenEvents()
    data class OnRegister(val imageUri: Uri?, val createUserDto: CreateUserDto) : AuthScreenEvents()
}

sealed class ResultEvents{
    data class OnError(val message: String): ResultEvents()
    data class OnSuccess(val message: String?): ResultEvents()
}

data class ValidationResult(
    val successful: Boolean,
    val error: String? = null
)