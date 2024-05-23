package com.example.instagramclonedemo.domain

import android.net.Uri
import com.example.instagramclonedemo.data.CreateUserDto
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    suspend fun createUserWithEmailAndPassword(email:String , password:String) : FirebaseUser?

    suspend fun signInWithEmailAndPassword(email: String , password: String): FirebaseUser?

    suspend fun checkUsernameAvailability(username: String): Boolean

    suspend fun signOut() : FirebaseUser?

    suspend fun getUser() : FirebaseUser?

    suspend fun sendPasswordResetEmail(email :String): Boolean

    suspend fun verifyPasswordResetCode(code: String)

    suspend fun saveUserProfile(createUserDto: CreateUserDto): Boolean

    suspend fun uploadProfileImage(imageUri: Uri): String

}