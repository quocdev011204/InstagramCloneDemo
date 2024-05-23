package com.example.instagramclonedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.instagramclonedemo.ui.theme.InstagramCloneDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstagramCloneDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InstagramApp()
                }
            }
        }
    }
}


@Composable
fun InstagramApp() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            if (BottomNavBarManager.showBottomNavBar) {
                // Hiển thị BottomNavBar
                BottomNavBar(navController)
            }
        },
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            InstagramNavGraph(navHostController = navController)
//            InstagramOtherNavGraph(navHostController = navController)
        }

    }
}

object BottomNavBarManager {
    var showBottomNavBar by mutableStateOf(false)
}

@Composable
fun updateBottomNavBarVisibility(show: Boolean) {
    // Mục đích của fun này tạo ra để cập nhật trạng thái ẩn hiện của BottomNavBar
    // Nó nhận về giá trị false, true để biết khi nào nên hiện khi nào không
    BottomNavBarManager.showBottomNavBar = show
}

@Composable
fun BottomNavBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screenList = listOf(
        BottomNavScreens.Home,
        BottomNavScreens.Search,
        BottomNavScreens.CreatePostNew,
        BottomNavScreens.Reels,
        BottomNavScreens.Profile,
    )

    BottomNavigation {
        screenList.forEach{screen ->

            BottomNavigationItem(
                selected = currentDestination?.route == screen.route,
                onClick = { navController.navigate(screen.route) },
                icon = {
                    if(screen.route == BottomNavRoutes.PROFILE.route){
                        CircularProfileView()
                    }else{
                        Icon(painter = painterResource(id = screen.icon), contentDescription = "Nav Icon", modifier = Modifier.size(24.dp))
                    }
                },
                modifier = Modifier.background(color = Color.White),
                selectedContentColor = Color.Red,
                unselectedContentColor = Color.Black
            )
        }
    }
}

@Composable
fun CircularProfileView(){
    Image(painter = painterResource(id = R.drawable.profile_tbq),
        contentDescription = "current user",
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}


@Preview(showBackground = true)
@Composable
fun ProfilePreview(){
    CircularProfileView()
}