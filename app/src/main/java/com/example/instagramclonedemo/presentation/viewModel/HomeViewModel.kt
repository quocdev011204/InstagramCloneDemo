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
    // HomeViewModel kế thừa từ ViewModel, cung cấp một lớp ViewModel để quản lý dữ liệu liên quan đến giao diện người dùng
    private val firestore = FirebaseFirestore.getInstance()
    //  FirebaseFirestore.getInstance(), để tương tác với cơ sở dữ liệu Firestore.
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    // _posts: Biến này là một MutableStateFlow chứa một danh sách các đối tượng Post. MutableStateFlow, dùng để lưu trữ trạng thái và lưu trữ danh sách bài viết
    val posts: StateFlow<List<Post>> = _posts
    //posts: là một StateFlow, là phiên bản chỉ đọc của _posts. Nó được sử dụng để công khai dữ liệu ra bên ngoài mà không cho phép thay đổi từ bên ngoài.

    init {
        // Khối init là một khối khởi tạo, sẽ được chạy khi HomeViewModel được khởi tạo.
        viewModelScope.launch {
            // Khởi tạo coroutine để thao tác bất đồng bộ tránh bị huỷ khi ViewModel bị huỷ
            firestore.collection("posts").addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    val postsList = it.documents.map { doc ->
                        doc.toObject<Post>() ?: Post()
                        // Duyệt qua từng tài liệu trong snapshot và chuyển đổi mỗi tài liệu thành một đối tượng Post bằng cách sử dụng toObject<Post>()
                        // Nếu chuyển đổi thất bại thì sử dụng đối tượng Post mặc định
                    }
                    _posts.value = postsList
                }
            }
        }
    }

}