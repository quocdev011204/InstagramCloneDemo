package com.example.instagramclonedemo.presentation.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramclonedemo.data.CreateUserDto
import com.example.instagramclonedemo.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class CreatePostNewViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private var currentUser: CreateUserDto? = null

    // Hàm để cập nhật thông tin của người dùng hiện tại
    fun setCurrentUser(user: CreateUserDto) {
        currentUser = user
    }

    fun createPost(
        content: String,
        mediaUri: Uri?,
        profile: String,
        username: String,
        postImageList: List<Int>,
        likedBy: List<CreateUserDto>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val user = auth.currentUser ?: throw Exception("User not logged in")
                val postId = UUID.randomUUID().toString()
                var mediaUrl: String? = null

                mediaUri?.let {
                    val storageRef = storage.reference.child("posts/$postId/${mediaUri.lastPathSegment}")
                    val uploadTask = storageRef.putFile(mediaUri).await()
                    mediaUrl = uploadTask.storage.downloadUrl.await().toString()
                }

                val post = Post(
                    userId = user.uid,
                    content = content,
                    mediaUrl = mediaUrl,
                    timestamp = System.currentTimeMillis(),
                    profile = profile,
                    username = username,
                    postImageList = postImageList,
                    likedBy = likedBy
                )

                firestore.collection("posts").document(postId).set(post).await()
                onSuccess()
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}