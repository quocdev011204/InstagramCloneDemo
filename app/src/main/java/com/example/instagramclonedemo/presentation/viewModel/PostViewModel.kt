package com.example.instagramclonedemo.presentation.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramclonedemo.data.CreateUserDto
import com.example.instagramclonedemo.model.Comment
import com.example.instagramclonedemo.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class CreatePostNewViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    // Khởi tạo đối tượng Firestore để tương tác với cơ sở dữ liệu Firestore
    private val storage =
        FirebaseStorage.getInstance() // Khởi tạo để lưu trữ các tệp phượng tiện( media files )
    private val auth = FirebaseAuth.getInstance()  // xác thực người dùng

    private var currentUser: CreateUserDto? = null
    // Biến lưu trữ thông tin của người dùng hiện tại dưới dạng một đối tượng CreateUserDto.

    // Hàm để cập nhật thông tin của người dùng hiện tại
    fun setCurrentUser(user: CreateUserDto) {
        // cập nhật thông tin của người dùng hiện tại bằng cách gán giá trị user cho biến currentUser.
        currentUser = user
    }



    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

    // Fetch comments
    fun fetchComments(postId: String) {
        viewModelScope.launch {
            try {
                val postSnapshot = firestore.collection("posts").document(postId).get().await()
                val post = postSnapshot.toObject(Post::class.java)
                post?.let {
                    val retrievedComments = post.comments
                    _comments.value = retrievedComments
                } ?: throw Exception("Post not found")
            } catch (e: Exception) {
                Log.e("CreatePostNewViewModel", "Failed to fetch comments: ${e.message}")
            }
        }
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
                // Kiểm tra người dùng có đăng nhập không, nếu không ném ra ngoại lệ
                val postId = UUID.randomUUID().toString()
                // Tạo Id cho post nhưng là duy nhất
                var mediaUrl: String? = null

                mediaUri?.let {
                    val storageRef =
                        storage.reference.child("posts/$postId/${mediaUri.lastPathSegment}")
                    val uploadTask = storageRef.putFile(mediaUri).await()
                    mediaUrl = uploadTask.storage.downloadUrl.await().toString()
                }

                val post = Post(
                    postId = postId,
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
                // Lưu đối tượng post vào posts trên firestore
                onSuccess()
                // thành công
            } catch (e: Exception) {
                // thất bại
                onFailure(e)
            }
        }
    }


    fun deletePost(
        postId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                firestore.collection("posts").document(postId)
                    .delete()
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener { exception ->
                        onFailure(exception)
                    }
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }


    fun addComment(
        postId: String,
        comment: Comment,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                firestore.collection("posts").document(postId)
                    .update("comments", FieldValue.arrayUnion(comment))
                    .await()
                onSuccess()
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    fun getComments(postId: String, onSuccess: (List<Comment>) -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                val postSnapshot = firestore.collection("posts").document(postId).get().await()
                val post = postSnapshot.toObject(Post::class.java)
                post?.let {
                    val retrievedComments = post.comments
                    onSuccess(retrievedComments)
                } ?: onFailure(Exception("Post not found"))
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    fun likePost(
        postId: String,
        userId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val postRef = firestore.collection("posts").document(postId)
                val postSnapshot = postRef.get().await()
                val likedByList =
                    postSnapshot.toObject(Post::class.java)?.likedBy?.map { it.userId } ?: listOf()

                if (!likedByList.contains(userId)) {
                    val updatedLikedBy = likedByList + userId
                    val likesCount = updatedLikedBy.size.toLong()

                    val updates = hashMapOf<String, Any>(
                        "likedBy" to updatedLikedBy.map { CreateUserDto(userId = it) },
                        "likesCount" to likesCount
                    )

                    postRef.update(updates).await()
                    onSuccess()
                } else {
                    // User has already liked this post
                    onSuccess() // Consider calling onFailure here if you want to handle this case differently
                }
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }



        fun unlikePost(
            postId: String,
            userId: String,
            onSuccess: () -> Unit,
            onFailure: (Exception) -> Unit
        ) {
            viewModelScope.launch {
                try {
                    val postRef = firestore.collection("posts").document(postId)
                    val postDoc = postRef.get().await()
                    val likedBy = postDoc.toObject<Post>()?.likedBy ?: emptyList()
                    val updatedLikedBy = likedBy.filter { it.userId != userId }

                    postRef.update(
                        mapOf(
                            "likedBy" to updatedLikedBy,
                            "likesCount" to updatedLikedBy.size
                        )
                    ).await()

                    onSuccess()
                } catch (e: Exception) {
                    onFailure(e)
                }
            }
        }
    }


