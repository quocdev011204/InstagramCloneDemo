package com.example.instagramclonedemo.domain.use_cases

data class AuthenticationUseCases(
    val isUserAuthenticated: IsUserAuthenticated,
    val firebaseAuthState: FirebaseAuthState,
    val firebaseSignIn: FirebaseSignIn,
    val firebaseSignUp: FirebaseSignUp,
    val firebaseSignOut: FirebaseSignOut
) {

}
