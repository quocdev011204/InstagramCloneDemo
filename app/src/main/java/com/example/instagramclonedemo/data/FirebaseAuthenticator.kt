package com.example.instagramclonedemo.data

import android.net.Uri
import com.example.instagramclonedemo.common.utils.Constants
import com.example.instagramclonedemo.domain.Authenticator
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class FirebaseAuthenticator : Authenticator {
    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseUser? {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
        Firebase.auth.currentUser?.sendEmailVerification()?.await()
        // Gửi email xác thực
        return Firebase.auth.currentUser
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseUser? {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
        return Firebase.auth.currentUser
    }

    override suspend fun checkUsernameAvailability(username: String): Boolean {
        val querySnapshot = Firebase.firestore.collection(Constants.USERS_COLLECTION)
            .whereEqualTo("username", username)
            .get()
            .await()

        return querySnapshot.documents
            .map { it.getString("username") }
            .none { it.equals(username, true) }
    }

    override suspend fun signOut(): FirebaseUser? {
        Firebase.auth.signOut()
        return Firebase.auth.currentUser
    }

    override suspend fun getUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        Firebase.auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun verifyPasswordResetCode(code: String) {
        Firebase.auth.verifyPasswordResetCode(code)
    }

    override suspend fun saveUserProfile(createUserDto: CreateUserDto) {
        val uid = createUserDto.userId ?: throw IllegalArgumentException("UID cannot be null")
        Firebase.firestore.collection(Constants.USERS_COLLECTION)
            .document(uid)
            .set(createUserDto)
            .await()
    }


    override suspend fun uploadUserProfile(imageUri: Uri): String {
        val uploadTask = Firebase.storage.reference
            .child("${Constants.PROFILE_IMAGE_STORAGE_REF}/image_${System.currentTimeMillis()}")
            .putFile(Uri.parse(imageUri.toString())).await()

        return uploadTask.storage.downloadUrl.await().toString()
    }
}