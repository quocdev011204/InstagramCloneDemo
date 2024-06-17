package com.example.instagramclonedemo.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramclonedemo.data.CreateUserDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class ProfileOtherUserViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    var user by mutableStateOf<CreateUserDto?>(null)
        // lưu trữ thông tin người dùng dưới dạng CreateUserDto?
        // mutableStateOf dùng để theo dõi và cập nhật trạng thái người dùng
        private set
        // Đặt phương thức setter của user là private, để chỉ có thể thay đổi trong ProfileOtherUserViewModel

    fun loadUser(userId: String) {
        viewModelScope.launch {
            db.collection("users").document(userId).get()
                // Truy vấn trong users dựa trên userId
                .addOnSuccessListener { document ->
                    // Truy vấn thành công thì chuyển đổi document thành đối tượng CreateUserDto và gán cho user
                    user = document.toObject(CreateUserDto::class.java)
                }
                .addOnFailureListener {
                    user = null
                }
        }
    }
}