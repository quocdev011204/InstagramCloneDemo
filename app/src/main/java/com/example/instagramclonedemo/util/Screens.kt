package com.example.instagramclonedemo.util

sealed class Screens(val route: String){
    object SplashScreen: Screens(route ="SplashScreen")
    object LoginScreen: Screens(route ="LoginScreen")
    object RegisterScreen: Screens(route = "SignUpScreen")
    object SingleMessageScreen: Screens(route = "SingleMessageScreen")
    object MessagesListScreenDemo: Screens(route = "MessagesListScreenDemo")
    object ChatScreen: Screens(route = "ChatScreen")
    object NotificationsScreen: Screens(route = "NotificationsScreen")
    object UpdateProfileScreen: Screens(route = "UpdateProfileScreen")

    object ProfileOtherUserScreen: Screens(route = "ProfileOtherUserScreen")

    object ChangePasswordUserScreen: Screens(route = "ChangePasswordUserScreen")

    object ChangeInformationPostScreen: Screens(route = "ChangeInformationPostScreen")
}
