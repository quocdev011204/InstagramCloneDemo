package com.example.instagramclonedemo.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramclonedemo.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    init {
        viewModelScope.launch {
            firestore.collection("posts").addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    val postsList = it.documents.map { doc ->
                        doc.toObject<Post>() ?: Post()
                    }
                    _posts.value = postsList
                }
            }
        }
    }
}