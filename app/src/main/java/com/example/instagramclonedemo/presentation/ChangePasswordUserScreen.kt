package com.example.instagramclonedemo.presentation

import androidx.compose.foundation.background
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
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.instagramclonedemo.common.components.CustomFormTextField
import com.example.instagramclonedemo.presentation.viewModel.ChangePasswordUserViewModel
import com.example.instagramclonedemo.ui.theme.AccentColor
import com.example.instagramclonedemo.updateBottomNavBarVisibility
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterialApi::class)
@Composable
fun ChangePasswordUserScreen(navController: NavController, viewModel: ChangePasswordUserViewModel = hiltViewModel()) {

    updateBottomNavBarVisibility(false)
    // Cho phép cập nhật giá trị khi trạng thái thay đổi
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showMessage by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    // var : có thể thay đổi, val không thể thay đổi

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row ( modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.size(30.dp))
            }
            Text(
                text = "Change password",
                style = MaterialTheme.typography.h5,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
        Text(
            text = "Your password must be at least 6 characters and should include a combination of numbers, letters and special characters (!\$@%).",
            style = MaterialTheme.typography.body2,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
        )
        CustomFormTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            visualTransformation = PasswordVisualTransformation(),
            hint = "Current password",
            value = oldPassword,
            keyboardType = KeyboardType.Password,
            onValueChange = { oldPassword = it },
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomFormTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            visualTransformation = PasswordVisualTransformation(),
            hint = "New password",
            value = newPassword,
            keyboardType = KeyboardType.Password,
            onValueChange = { newPassword = it },
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomFormTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            hint = "Re-type new password",
            visualTransformation = PasswordVisualTransformation(),
            value = confirmPassword,
            keyboardType = KeyboardType.Password,
            onValueChange = { confirmPassword = it },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.oldPassword = oldPassword
                viewModel.newPassword = newPassword
                viewModel.confirmPassword = confirmPassword
                viewModel.changePassword { success, msg ->
                    message = msg
                    showMessage = true
                    if (success) {
                        oldPassword = ""
                        newPassword = ""
                        confirmPassword = ""
                    }
                    coroutineScope.launch {
                        delay(2000)
                        showMessage = false
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = AccentColor)
        ) {
            Text("Change password",
                color = Color.White
                )
        }
        if (showMessage) {
            Text(text = message, color = if (message.contains("thành công")) MaterialTheme.colors.primary else MaterialTheme.colors.error)
            Button(onClick = { showMessage = false }) {
                Text("Đóng")
            }
        }
    }
}