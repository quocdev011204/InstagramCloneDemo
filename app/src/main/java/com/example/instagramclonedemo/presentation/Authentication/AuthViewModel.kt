package com.example.instagramclonedemo.presentation.Authentication

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramclonedemo.data.CreateUserDto
import com.example.instagramclonedemo.domain.AuthRepository
import com.example.instagramclonedemo.domain.AuthValidator
import com.example.instagramclonedemo.presentation.AuthScreenEvents
import com.example.instagramclonedemo.presentation.ResultEvents
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    // AuthViewModel được khởi tạo và phụ thuộc vào AuthRepository và FirebaseAuth thông qua Dependency Injection (@Inject)

    // AuthViewModel là một lớp ViewModel
    // Nó tương tác với một AuthRepository để thực hiện các thao tác xác thực và quản lý trạng thái cũng như sự kiện liên quan đến xác thực

    private val eventChannel = Channel<ResultEvents>()
    val eventChannelFlow = eventChannel.receiveAsFlow()
    //eventChannel được sử dụng để gửi các sự kiện kết quả (thành công hoặc lỗi) và eventChannelFlow để nhận các sự kiện này dưới dạng Flow.



    var isLoading by mutableStateOf(false)
        private set



    //User Login_register


    fun onUserEvents(authScreenEvents: AuthScreenEvents) {
        // Sử dụng câu lệnh when để kiểm tra loại sự kiện xác thực (AuthScreenEvents)
        when (authScreenEvents) {
            is AuthScreenEvents.OnLogin -> {
                val email = authScreenEvents.email
                val password = authScreenEvents.password
                // kiểm tra tính hợp lệ bằng AuthValidator.validateSignInRequest
                val result = AuthValidator.validateSignInRequest(email, password)
                if (result.successful) {
                    signIn(email, password)
                } else {
                    viewModelScope.launch {
                        eventChannel.send(ResultEvents.OnError(result.error!!))
                    }
                }
            }
            is AuthScreenEvents.OnRegister -> {
                if (authScreenEvents.imageUri == null) {
                    viewModelScope.launch {
                        eventChannel.send(ResultEvents.OnError("You have not selected an image"))
                    }
                    return
                }

                val result = AuthValidator.validateCreateUserRequest(authScreenEvents.createUserDto)
                if (result.successful) {
                    createUser(authScreenEvents.imageUri, authScreenEvents.createUserDto)
                } else {
                    viewModelScope.launch {
                        eventChannel.send(ResultEvents.OnError(result.error!!))
                    }
                }
            }

            else -> {}
        }
    }


    private fun createUser(imageUri: Uri, createUserDto: CreateUserDto) = viewModelScope.launch {
        isLoading = true
        // isLoading thành true để biểu thị trạng thái đang xử lý
        try {
            val usernameAvailability = authRepository.checkUsernameAvailability(createUserDto.username)
            if (!usernameAvailability) {
                isLoading = false
                eventChannel.send(ResultEvents.OnError("Username entered already exist, try another one."))
            } else {
                val firebaseUser = authRepository.createUserWithEmailAndPassword(
                    createUserDto.email,
                    createUserDto.password
                )
                firebaseUser?.let { user ->
                    val imageUrl = authRepository.uploadProfileImage(imageUri)

                    val updatedUser = createUserDto.copy(
                        uid = user.uid,
                        createdDate = FieldValue.serverTimestamp(),
                        imageUrl = imageUrl,
                        password = "",
                    )
                    authRepository.saveUserProfile(updatedUser)
                }

                isLoading = false
                eventChannel.send(ResultEvents.OnSuccess("User created, kindly verify your mail to login"))
            }
        } catch (e: Exception) {
            isLoading = false
            eventChannel.send(
                ResultEvents.OnError(
                    e.localizedMessage ?: "Unable to Create User, try again."
                )
            )
        }
    }


    private fun signIn(email: String, password: String) = viewModelScope.launch {
        isLoading = true
        try {
            val firebaseUser = authRepository.signInWithEmailAndPassword(email, password)
            isLoading = false

            firebaseUser?.let { user ->
                if (user.isEmailVerified) {
                    eventChannel.send(ResultEvents.OnSuccess("Login successful"))
                } else {
                    eventChannel.send(ResultEvents.OnError("User email is not verified. Kindly verify to login"))
                }
            }
        } catch (e: Exception) {
            isLoading = false
            eventChannel.send(ResultEvents.OnError(e.localizedMessage ?: "Unable to Login User, try again."))
        }
    }


    fun getCurrentUser() = firebaseAuth.currentUser



}