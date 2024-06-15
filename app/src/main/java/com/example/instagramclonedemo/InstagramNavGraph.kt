package com.example.instagramclonedemo

//import com.example.instagramclonedemo.presentation.MessagesListScreen
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.instagramclonedemo.presentation.ChangePasswordUserScreen
import com.example.instagramclonedemo.presentation.LoginScreen
import com.example.instagramclonedemo.presentation.ProfileOtherUserSreen
import com.example.instagramclonedemo.presentation.RegisterScreen
import com.example.instagramclonedemo.presentation.SplashScreen
import com.example.instagramclonedemo.presentation.UpdateProfileScreen
import com.example.instagramclonedemo.presentation.chat.ChatScreen
import com.example.instagramclonedemo.presentation.viewModel.CreatePostNewViewModel
import com.example.instagramclonedemo.presentation.viewModel.FirebaseViewModel
import com.example.instagramclonedemo.presentation.viewModel.TaskViewModel
import com.example.instagramclonedemo.screens.CreatePostNew
import com.example.instagramclonedemo.screens.HomeScreen
import com.example.instagramclonedemo.screens.ProfileScreen
import com.example.instagramclonedemo.screens.ReelsScreen
import com.example.instagramclonedemo.screens.SearchScreen
import com.example.instagramclonedemo.util.Screens


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InstagramNavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController , startDestination = Screens.SplashScreen.route){
        composable(route = BottomNavRoutes.HOME.route){
            HomeScreen(navController = navHostController)
        }
        composable(route = BottomNavRoutes.SEARCH.route) {
            SearchScreen(navigateToUserProfile = { userId ->
                navHostController.navigate(Screens.ProfileOtherUserScreen.route + "/$userId")
            })
        }
        composable(route = BottomNavRoutes.REELS.route){
            ReelsScreen()
        }
        composable(route = BottomNavRoutes.CREATEPOSTNEW.route){
            CreatePostNew(viewModel = CreatePostNewViewModel(),
                onPostCreated = {
                    navHostController.navigate(BottomNavScreens.Home.route)
                })
        }
        composable(route = BottomNavRoutes.PROFILE.route){
            ProfileScreen(navController = navHostController)
        }
        composable(route = Screens.ProfileOtherUserScreen.route + "/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            ProfileOtherUserSreen(navController = navHostController, userId = userId)
        }
        composable(route = Screens.SplashScreen.route){
            SplashScreen(navController = navHostController)
        }
        composable(route = Screens.LoginScreen.route){
            LoginScreen(navController = navHostController)
        }
        composable(route = Screens.RegisterScreen.route){
            RegisterScreen(navController = navHostController)
        }
        composable(route = Screens.ChatScreen.route){
            ChatScreen(navController = navHostController, firebaseViewModel = FirebaseViewModel(), taskViewModel = TaskViewModel())
        }
        composable(route = Screens.UpdateProfileScreen.route){
            UpdateProfileScreen(navController = navHostController)
        }
        composable(route = Screens.ChangePasswordUserScreen.route){
            ChangePasswordUserScreen(navController = navHostController)
        }
    }
}

