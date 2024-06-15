package com.example.instagramclonedemo.presentation.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UpdateUserViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val storageRef = FirebaseStorage.getInstance().reference

    fun updateUserProfile(
        userId: String,
        username: String,
        fullName: String,
        imageUrl: Uri?,
        bio: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val updates = mutableMapOf<String, Any>()
                if (username.isNotBlank()) updates["username"] = username
                if (fullName.isNotBlank()) updates["fullName"] = fullName
                if (bio.isNotBlank()) updates["bio"] = bio  // Thêm bio vào updates nếu không trống
                imageUrl?.let { uri ->
                    val imageFileName = "profile_${System.currentTimeMillis()}.jpg"
                    val imageRef = storageRef.child("images/$imageFileName")
                    val uploadTask = imageRef.putFile(uri)

                    uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let { throw it }
                        }
                        imageRef.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUri = task.result
                            updates["imageUrl"] = downloadUri.toString()

                            if (updates.isNotEmpty()) {
                                firestore.collection("users").document(userId)
                                    .update(updates)
                                    .addOnCompleteListener { updateTask ->
                                        if (updateTask.isSuccessful) {
                                            onSuccess()
                                        } else {
                                            onFailure(updateTask.exception ?: Exception("Unknown error"))
                                        }
                                    }
                            } else {
                                onSuccess()
                            }
                        } else {
                            onFailure(task.exception ?: Exception("Unknown error"))
                        }
                    }.await()
                } ?: run {
                    if (updates.isNotEmpty()) {
                        firestore.collection("users").document(userId)
                            .update(updates)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    onSuccess()
                                } else {
                                    onFailure(updateTask.exception ?: Exception("Unknown error"))
                                }
                            }
                    } else {
                        onSuccess()
                    }
                }
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}