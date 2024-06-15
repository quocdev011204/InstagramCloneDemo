package com.example.instagramclonedemo.presentation

import android.net.Uri
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
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.instagramclonedemo.BottomNavScreens
import com.example.instagramclonedemo.common.components.CustomFormTextField
import com.example.instagramclonedemo.presentation.viewModel.UpdateUserViewModel
import com.example.instagramclonedemo.ui.theme.AccentColor
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UpdateProfileScreen(
    navController: NavController, // assuming you use NavController for navigation
    viewModel: UpdateUserViewModel = hiltViewModel()
) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userId = currentUser?.uid ?: ""

    var username by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf<Uri?>(null) }
    var bio by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUrl = uri  // Store the Uri object directly
    }

    val updateProfile = {
        viewModel.updateUserProfile(
            userId = userId,
            username = username,
            fullName = fullName,
            imageUrl = imageUrl,
            bio = bio,
            onSuccess = {
                // Navigate to ProfileScreen after success
                navController.navigate(BottomNavScreens.Profile.route)
            },
            onFailure = { e ->
                // Handle the failure case
                println("Failed to update profile: ${e.message}")
            }
        )
    }

    // UI for updating user profile
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
                text = "Update your profile",
                style = MaterialTheme.typography.h5,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomFormTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            hint = "Username",
            value = username,
            keyboardType = KeyboardType.Text,
            onValueChange = { username = it },
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomFormTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            hint = "Full name",
            value = fullName,
            keyboardType = KeyboardType.Text,
            onValueChange = { fullName = it },
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomFormTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            hint = "Bio",
            value = bio,
            keyboardType = KeyboardType.Text,
            onValueChange = { bio = it },
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { launcher.launch("image/*") },
            ) {
                Text("Select Image/Video")
            }
        }
        imageUrl?.let {
                Image(
                    painter = rememberImagePainter(it),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = updateProfile,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(backgroundColor = AccentColor)
        ) {
            Text(
                "Update Profile",
                color = Color.White
            )
        }
    }
}