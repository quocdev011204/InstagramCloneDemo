package com.example.instagramclonedemo.presentation

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.instagramclonedemo.common.components.CustomFormTextField
import com.example.instagramclonedemo.presentation.viewModel.ChangeInformationPostViewModel
import com.example.instagramclonedemo.updateBottomNavBarVisibility


@Composable
fun ChangeInformationPostScreen(
    navController: NavController,
    postId: String,
    viewModel: ChangeInformationPostViewModel = hiltViewModel()
) {
    updateBottomNavBarVisibility(false)

    val context = LocalContext.current
    var newContent by remember { mutableStateOf("") }
    var newImageUrl by remember { mutableStateOf<Uri?>(null) }


    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        newImageUrl = uri
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row() {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(30.dp)
                )
            }
            Text(
                text = "Change Information Your Post",
                style = MaterialTheme.typography.h5,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Text field để nhập nội dung mới cho bài viết
        CustomFormTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            hint = "What's on your mind?",
            value = newContent,
            onValueChange = { newContent = it },
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { launcher.launch("image/*") },
            ) {
                Text("Pick Image")
            }
        }

        // Hiển thị ảnh hiện tại của bài viết
        newImageUrl?.let { imageUrl ->
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button để cập nhật thông tin bài viết
        Button(
            onClick = {
                viewModel.content = newContent
                viewModel.mediaUrl = newImageUrl

                viewModel.updatePost(postId,
                    onSuccess = {
                        Toast.makeText(context, "Updated post successfully", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    },
                    onFailure = { exception ->
                        Toast.makeText(context, "Failed to update post: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Update Post")
        }
    }
}





