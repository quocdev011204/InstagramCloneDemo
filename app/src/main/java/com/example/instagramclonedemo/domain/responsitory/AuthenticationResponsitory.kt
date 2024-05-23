package com.example.instagramclonedemo.domain.responsitory

import com.example.instagramclonedemo.util.Response
import kotlinx.coroutines.flow.Flow

interface AuthenticationResponsitory {
    fun isUserAuthenticatedInFirebase() : Boolean

    // được sử dụng để lắng nghe trạng thái xác thực người dùng trong Firebase. Khi trạng thái thay đổi, luồng sẽ phát ra giá trị true nếu người dùng đã xác thực và false nếu ngược lại.
    fun getFirebaseAuthState() : Flow<Boolean>

    fun firebaseSignIn(email: String, password: String) : Flow<Response<Boolean>>

    fun firebaseSignOut() : Flow<Response<Boolean>>

    fun firebaseSignUp(email: String, password: String, userName: String) : Flow<Response<Boolean>>


}