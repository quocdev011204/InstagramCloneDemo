package com.example.instagramclonedemo.presentation

import androidx.navigation.NavController
import com.example.instagramclonedemo.util.Screens
import com.google.firebase.auth.FirebaseAuth

fun LogOut(navController: NavController) {
    try {
        FirebaseAuth.getInstance().signOut()
        navController.navigate(Screens.LoginScreen.route) {
            popUpTo(Screens.LoginScreen.route) {
                inclusive = true
            }
        }
    } catch (e: Exception) {
        // Xử lý lỗi khi có Exception xảy ra
        // Ví dụ: Log.e("Logout", "Error: ${e.message}")
    }
}