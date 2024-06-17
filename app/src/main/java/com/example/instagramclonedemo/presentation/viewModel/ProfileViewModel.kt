package com.example.instagramclonedemo.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramclonedemo.data.CreateUserDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val _user = MutableStateFlow<CreateUserDto?>(null)
    val user: StateFlow<CreateUserDto?> = _user

    fun fetchUser(userId: String) {
        // Tải thông tin người dùng
        viewModelScope.launch {
            firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    val user = document.toObject(CreateUserDto::class.java)
                    _user.value = user
                }
                .addOnFailureListener { e ->
                    // Xử lý lỗi
                }
        }
    }
}