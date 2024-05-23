package com.example.instagramclonedemo.domain.use_cases


import com.example.instagramclonedemo.domain.responsitory.AuthenticationResponsitory
import javax.inject.Inject

class FirebaseSignUp @Inject constructor(
    private val responsitory: AuthenticationResponsitory
)  {
    operator fun invoke(email: String, password: String, userName: String) = responsitory.firebaseSignUp(email, password,userName)
}