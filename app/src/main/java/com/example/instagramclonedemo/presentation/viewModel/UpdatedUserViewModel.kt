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
    //  tham chiếu đến gốc của dịch vụ lưu trữ Firebase. giúp tải lên, tải xuống các tệp tin trong ứng dụng của mình

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
                // tạo updates để lưu thông tin người dùng cần cập nhật
                if (username.isNotBlank()) updates["username"] = username
                if (fullName.isNotBlank()) updates["fullName"] = fullName
                if (bio.isNotBlank()) updates["bio"] = bio
                imageUrl?.let { uri ->
                    val imageFileName = "profile_${System.currentTimeMillis()}.jpg"
                    // tạo tên file ảnh dựa trên thời gian
                    val imageRef = storageRef.child("images/$imageFileName")
                    // tạo đường dẫn trong FireStorage
                    val uploadTask = imageRef.putFile(uri)
                    // tải tệp imageUrl lên imageRef bằng gọi đến putFile

                    uploadTask.continueWithTask { task ->
                        // sử dụng continueWithTask để tiếp tục các tác vụ sau khi tệp tin tải lên thành công
                        if (!task.isSuccessful) {
                            task.exception?.let { throw it }
                        }
                        imageRef.downloadUrl // lấy đường dẫn khi tải lên tệp tin thành công
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUri = task.result
                            // Lấy URL của tệp tin đã tải lên bằng task.result.
                            updates["imageUrl"] = downloadUri.toString()

                            if (updates.isNotEmpty()) {
                                firestore.collection("users").document(userId)
                                    .update(updates)
                                    // cập nhật người dùng
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
                    }.await() // .await() là một phần của coroutine, giúp đợi cho đến khi tác vụ hoàn thành mà không khóa luồng hiện tại.
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