package com.example.instagramclonedemo.presentation

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.instagramclonedemo.R
import com.example.instagramclonedemo.common.components.CustomFormTextField
import com.example.instagramclonedemo.common.components.CustomRaisedButton
import com.example.instagramclonedemo.common.components.ImagePickerPermissionChecker
import com.example.instagramclonedemo.data.CreateUserDto
import com.example.instagramclonedemo.presentation.Authentication.AuthViewModel
import com.example.instagramclonedemo.ui.theme.IconDark
import com.example.instagramclonedemo.ui.theme.IconLight
import com.example.instagramclonedemo.ui.theme.LightGray
import com.example.instagramclonedemo.ui.theme.LineColor
import com.example.instagramclonedemo.updateBottomNavBarVisibility
import com.example.instagramclonedemo.util.Screens
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    updateBottomNavBarVisibility(false)
    val darkTheme: Boolean = isSystemInDarkTheme()
    val scaffoldState = rememberScaffoldState()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(key1 = true) {
        viewModel.eventChannelFlow.collectLatest { events:ResultEvents ->
            when (events) {
                is ResultEvents.OnError -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = events.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is ResultEvents.OnSuccess -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = events.message!!,
                        duration = SnackbarDuration.Short
                    )
                    navController.navigate(Screens.LoginScreen.route)
                }
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp)
                    .clickable { navController.navigate(Screens.LoginScreen.route) }
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                ImageSection(darkTheme, imageUri, coroutineScope, bottomSheetState)

                Spacer(modifier = Modifier.height(40.dp))

                FormSection(imageUri, viewModel)

                Spacer(modifier = Modifier.height(38.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp)
                ) {
                    Text(
                        text = "Have an account?",
                        style = MaterialTheme.typography.button,
                        color = LightGray,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Log in",
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                            navController.navigate(Screens.LoginScreen.route)
                        }
                    )
                }
            }

            BottomSection()
        }

    }

    ImagePickerPermissionChecker(
        coroutineScope,
        bottomSheetState,
        onCameraLaunchResult = { uri ->
            imageUri = uri
        },
        onGalleryLaunchResult = { uri ->
            imageUri = uri
        }
    )
}

@Composable
fun BoxScope.BottomSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
    ) {

        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(LineColor)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
        ) {
            Text(
                text = "Instagram from Meta",
                style = MaterialTheme.typography.button,
                color = LightGray,
            )
        }
    }
}

@Composable
fun FormSection(imageUri: Uri?, viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    CustomFormTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        hint = "Email",
        keyboardType = KeyboardType.Email,
        value = email,
        onValueChange = { email = it }
    )

    Spacer(modifier = Modifier.height(12.dp))

    CustomFormTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        hint = "Full Name",
        value = fullName,
        onValueChange = { fullName = it }
    )

    Spacer(modifier = Modifier.height(12.dp))

    CustomFormTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        hint = "Username",
        value = username,
        onValueChange = { username = it }
    )

    Spacer(modifier = Modifier.height(12.dp))

    CustomFormTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        visualTransformation = PasswordVisualTransformation(),
        hint = "Password",
        value = password,
        onValueChange = { password = it }
    )

    Spacer(modifier = Modifier.height(30.dp))

    CustomRaisedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = "Sign up",
        isLoading = viewModel.isLoading
    ) {
        val createUserDto = CreateUserDto(
            email = email.trim(),
            fullName = fullName,
            username = username.trim(),
            password = password.trim(),
        )
        viewModel.onUserEvents(
            AuthScreenEvents.OnRegister(
                imageUri = imageUri,
                createUserDto
            )
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageSection(
    darkTheme: Boolean,
    imageUri: Uri?,
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState
) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .border(
                width = 2.dp,
                color = if (darkTheme) IconDark else IconLight,
                shape = CircleShape
            )
            .padding(10.dp)
            .size(90.dp)
    ) {
        Image(
            painter = if (imageUri == null) {
                painterResource(id = R.drawable.ic_user)
            } else rememberAsyncImagePainter(model = Uri.parse(imageUri.toString())),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(90.dp)
                .clickable {
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }
                }
        )
    }
}