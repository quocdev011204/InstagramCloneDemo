package com.example.instagramclonedemo.util

sealed class Screens(val route: String){
    object SplashScreen: Screens(route ="SplashScreen")
    object LoginScreen: Screens(route ="LoginScreen")
    object RegisterScreen: Screens(route = "SignUpScreen")
    object SingleMessageScreen: Screens(route = "SingleMessageScreen")
    object MessagesListScreenDemo: Screens(route = "MessagesListScreenDemo")
    object MessagesListScreen: Screens(route = "MessagesListScreen")
    object NotificationsScreen: Screens(route = "NotificationsScreen")
}