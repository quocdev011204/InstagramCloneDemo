package com.example.instagramclonedemo

sealed class BottomNavScreens(
    val title : String,
    val icon: Int = R.drawable.home,
    val route: String
){
    object Home: BottomNavScreens(title = "home", icon = R.drawable.home, route = BottomNavRoutes.HOME.route)
    object Search: BottomNavScreens(title = "search", icon = R.drawable.search, route = BottomNavRoutes.SEARCH.route)
    object Reels: BottomNavScreens(title = "reels", icon = R.drawable.reels, route = BottomNavRoutes.REELS.route)
    object CreatePostNew: BottomNavScreens(title = "Create post new", icon = R.drawable.add, route = BottomNavRoutes.CREATEPOSTNEW.route)
    object Profile: BottomNavScreens(title = "profile", route = BottomNavRoutes.PROFILE.route)
}

enum class BottomNavRoutes(val route: String){
    HOME("home"),
    SEARCH("search"),
    REELS("reels"),
    CREATEPOSTNEW("notification"),
    PROFILE("profile"),
    MESSAGELIST("messagesList")
}
