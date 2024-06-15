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
        private set

    fun loadUser(userId: String) {
        viewModelScope.launch {
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    user = document.toObject(CreateUserDto::class.java)
                }
                .addOnFailureListener {
                    user = null
                }
        }
    }
}