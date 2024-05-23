package com.example.instagramclonedemo.domain.use_cases

import com.example.instagramclonedemo.domain.responsitory.AuthenticationResponsitory
import javax.inject.Inject

class IsUserAuthenticated @Inject constructor(
    private val responsitory: AuthenticationResponsitory
) {
    operator fun invoke() = responsitory.isUserAuthenticatedInFirebase()
}