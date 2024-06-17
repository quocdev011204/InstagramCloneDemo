@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.instagramclonedemo.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.instagramclonedemo.data.CreateUserDto
import com.example.instagramclonedemo.presentation.viewModel.SearchUserViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(navigateToUserProfile: (String) -> Unit, viewModel: SearchUserViewModel = hiltViewModel()) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val searchResults by viewModel.searchResults.collectAsState()

    val posts = ArrayList<String>()
    (0..26).forEach { index ->
        val post = "https://picsum.photos/400/300?random=$index"
        posts.add(post)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Thanh tìm kiếm
        BasicTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.searchUsers(it.text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                .padding(12.dp),
            decorationBox = { innerTextField ->
                Box(
                    Modifier
                        .fillMaxWidth()
                ) {
                    if (searchQuery.text.isEmpty()) {
                        Text("Tìm kiếm", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )

        // Hiển thị kết quả tìm kiếm nếu có
        if (searchResults.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                LazyColumn {
                    items(searchResults) { user ->
                        UserItem(user = user, onClick = {
                            navigateToUserProfile(user.userId ?: "")
                        })
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp)) // Tạo khoảng trống giữa kết quả tìm kiếm và PostSection
        }

        // Hiển thị các tấm ảnh
        PostSection(
            posts,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun UserItem(user: CreateUserDto, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = rememberImagePainter(data = user.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(user.username ?: "", style = MaterialTheme.typography.body1)
            Text(user.fullName ?: "", style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun PostSection(
    posts: List<String>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.scale(1.01f)
    ) {
        items(posts.size) {
            Image(
                painter = rememberAsyncImagePainter(posts[it]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(1f)
            )
        }
    }
}

