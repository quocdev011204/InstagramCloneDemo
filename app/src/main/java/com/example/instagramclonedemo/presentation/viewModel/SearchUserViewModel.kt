package com.example.instagramclonedemo.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramclonedemo.data.CreateUserDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchUserViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _searchResults = MutableStateFlow<List<CreateUserDto>>(emptyList())
    val searchResults: StateFlow<List<CreateUserDto>> = _searchResults

    fun searchUsers(query: String) {
        viewModelScope.launch {
            if (query.isNotEmpty()) {
                db.collection("users")
                    .whereGreaterThanOrEqualTo("username", query)
                    .whereLessThanOrEqualTo("username", query + '\uf8ff')
                    .get()
                    .addOnSuccessListener { documents ->
                        val users = documents.map { it.toObject(CreateUserDto::class.java) }
                        _searchResults.value = users
                    }
                    .addOnFailureListener {
                        _searchResults.value = emptyList()
                    }
            } else {
                _searchResults.value = emptyList()
            }
        }
    }

}