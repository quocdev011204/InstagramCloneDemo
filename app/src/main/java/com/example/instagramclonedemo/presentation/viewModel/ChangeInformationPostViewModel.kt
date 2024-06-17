package com.example.instagramclonedemo.presentation.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChangeInformationPostViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val storageRef = FirebaseStorage.getInstance().reference

    var content: String = ""
    var mediaUrl: Uri? = null

    fun updatePost(postId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                val updates = mutableMapOf<String, Any>()
                if (content.isNotBlank()) updates["content"] = content

                mediaUrl?.let { uri ->
                    val mediaFileName = "post_${System.currentTimeMillis()}.jpg"
                    val mediaRef = storageRef.child("images/$mediaFileName")
                    val uploadTask = mediaRef.putFile(uri)

                    uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let { throw it }
                        }
                        mediaRef.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUri = task.result
                            updates["mediaUrl"] = downloadUri.toString()

                            if (updates.isNotEmpty()) {
                                firestore.collection("posts").document(postId)
                                    .update(updates)
                                    .addOnCompleteListener { updateTask ->
                                        if (updateTask.isSuccessful) {
                                            onSuccess()
                                        } else {
                                            onFailure(updateTask.exception ?: Exception("Unknown error"))
                                        }
                                    }
                            } else {
                                onSuccess() // Không có gì để cập nhật
                            }
                        } else {
                            onFailure(task.exception ?: Exception("Unknown error"))
                        }
                    }.await()
                } ?: run {
                    if (updates.isNotEmpty()) {
                        firestore.collection("posts").document(postId)
                            .update(updates)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    onSuccess()
                                } else {
                                    onFailure(updateTask.exception ?: Exception("Unknown error"))
                                }
                            }
                    } else {
                        onSuccess() // Không có gì để cập nhật
                    }
                }
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}
