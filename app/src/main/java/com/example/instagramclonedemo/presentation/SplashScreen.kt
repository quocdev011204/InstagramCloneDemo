package com.example.instagramclonedemo.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.instagramclonedemo.R
import com.example.instagramclonedemo.util.Screens
import kotlinx.coroutines.delay

@Composable
@ExperimentalFoundationApi
fun SplashScreen(
    navController: NavController,
) {

    LaunchedEffect(key1 = true) {
        delay(1000L)
        navController.popBackStack()
        navController.navigate(Screens.LoginScreen.route)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(100.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
        ) {
            Text(
                text = "From",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
            Image(
                painter = painterResource(id = R.drawable.ic_meta),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

