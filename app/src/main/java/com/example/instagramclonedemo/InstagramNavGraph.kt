package com.example.instagramclonedemo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.instagramclonedemo.presentation.LoginScreen
//import com.example.instagramclonedemo.presentation.MessagesListScreen
import com.example.instagramclonedemo.presentation.RegisterScreen
import com.example.instagramclonedemo.presentation.SplashScreen
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
        composable(route = BottomNavRoutes.SEARCH.route){
            SearchScreen()
        }
        composable(route = BottomNavRoutes.REELS.route){
            ReelsScreen()
        }
        composable(route = BottomNavRoutes.CREATEPOSTNEW.route){
            CreatePostNew()
        }
        composable(route = BottomNavRoutes.PROFILE.route){
            ProfileScreen()
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

//        composable(route = Screens.MessagesListScreenDemo.route){
//            MessagesListScreenDemo(navController = navHostController)
//        }
//        composable(route = Screens.SingleMessageScreen.route){
//            val chatId=it.arguments?.getString("chatId")
//            chatId?.let {
//                SingleMessageScreen(navController=navHostController,chatId=chatId)
//            }
//        }
    }
}

