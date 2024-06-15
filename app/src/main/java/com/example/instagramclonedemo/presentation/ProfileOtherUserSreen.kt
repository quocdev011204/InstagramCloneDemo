package com.example.instagramclonedemo.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.instagramclonedemo.R
import com.example.instagramclonedemo.presentation.viewModel.ProfileOtherUserViewModel
import com.example.instagramclonedemo.screens.PostsSection
import com.example.instagramclonedemo.ui.theme.StoryHightlights
import com.example.instagramclonedemo.ui.theme.TabRowIcons


@Composable
fun ProfileOtherUserSreen (navController: NavController, userId: String?, viewModel: ProfileOtherUserViewModel = hiltViewModel() ){
    LaunchedEffect(userId) {
        if (userId != null) {
            viewModel.loadUser(userId)
        }
    }
//
//    val user = viewModel.user
//
//    if (user != null) {
//        Column(
//            modifier = Modifier.fillMaxSize().padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Image(
//                painter = rememberImagePainter(data = user.imageUrl),
//                contentDescription = null,
//                modifier = Modifier.size(100.dp),
//                contentScale = ContentScale.Crop
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(text = user.username ?: "Unknown User", style = MaterialTheme.typography.h6)
//        }
//    } else {
//        Text(text = "Loading...", style = MaterialTheme.typography.h6, modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center))
//    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarOtherUser(navController = navController, modifier = Modifier)
        },
        content = { contentPadding ->
            MainContentOtherUser(modifier = Modifier.padding(contentPadding))
        }
    )
}


@Composable
fun MainContentOtherUser(modifier: Modifier) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    Column(modifier.fillMaxSize()) {
        ProfileSectionOtherUser()
        Spacer(modifier = Modifier.height(20.dp))
        PostTabViewOtherUser( onTabSelected = { index ->
            selectedTabIndex = index
        })
        when(selectedTabIndex){
            0 -> PostsSection()
        }
    }
}


@Composable
fun ProfileSectionOtherUser(modifier: Modifier = Modifier, viewModel: ProfileOtherUserViewModel = hiltViewModel()) {
    val user = viewModel.user

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            ImageBuilderOtherUser(
                image = rememberAsyncImagePainter(model = user?.imageUrl),
                modifier = modifier
                    .size(100.dp)
                    .weight(3f)
            )
            com.example.instagramclonedemo.screens.FollowStatusBar(modifier = Modifier.weight(7f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        com.example.instagramclonedemo.screens.BioSection(
            name = user?.fullName ?: "Unknown User",
            activityLabel = "Education",
            descriptoion = user?.bio ?: "Unknown User",
            link = "https://www.facebook.com/thanh.ad.90",
            followers = buildAnnotatedString {
                val boldStyle = SpanStyle(
                    fontWeight = FontWeight.Bold
                )
                append("Followed by ")
                pushStyle(boldStyle)
                append("Elon Musk")
                pop()
                append(", ")
                pushStyle(boldStyle)
                append("Bill Gates")
                pop()
                append(" and")
                pushStyle(boldStyle)
                append(" 27 others")
            }
        )
        Spacer(modifier = Modifier.height(25.dp))
        ButtonSectionOtherUser(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(20.dp))
        HightlightSectionOtherUser(
            hightlight = listOf(
                StoryHightlights(
                    image = painterResource(id = R.drawable.dog),
                    title = "2023"
                ),
                StoryHightlights(
                    image = painterResource(id = R.drawable.hello),
                    title = "2024"
                ),
            )
        )

    }
}




@Composable
fun PostsSectionOtherUser() {
    val posts = listOf(
        painterResource(id = R.drawable.allena1),
        painterResource(id = R.drawable.helen),
        painterResource(id = R.drawable.aleena),
    )
    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.scale(1.01f), content = {
        items(posts.size){
            Image(
                painter = posts[it],
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .border(
                        width = 1.dp,
                        color = Color.White
                    ))
        }
    })

}

@Composable
fun PostTabViewOtherUser(
    modifier: Modifier = Modifier,
    onTabSelected:(selectedIndex: Int) -> Unit
) {
    var selectedTabIndex by remember{
        mutableStateOf(0)
    }
    val tabIcons = listOf(
        TabRowIcons(R.drawable.grid),
        TabRowIcons(R.drawable.mentions),
        TabRowIcons(R.drawable.reels)
    )
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier
    ) {
        tabIcons.forEachIndexed { index, tabRowIcons ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(index)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = tabRowIcons.icon),
                        contentDescription = "",
                        modifier.size(20.dp),
                        tint = if (selectedTabIndex == index) Color.Black else Color.Gray)
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray
            )
        }
    }
}




@Composable
fun HightlightSectionOtherUser(hightlight: List<StoryHightlights>, modifier: Modifier = Modifier) {
    LazyRow(modifier.padding(horizontal = 20.dp)){
        items(hightlight.size){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(end = 15.dp)
            ) {
                ImageBuilderOtherUser(image = hightlight[it].image, modifier = Modifier.size(70.dp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = hightlight[it].title, fontSize = 12.sp)
            }
        }
    }
}


@Composable
fun ButtonSectionOtherUser(modifier: Modifier) {
    val minWidth = 105.dp
    val height = 30.dp
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(horizontal = 20.dp)
    ) {
        SampleButtonOtherUser(
            modifier = Modifier
                .defaultMinSize(minWidth)
                .height(height),
            text = "Following",
            icon = Icons.Default.KeyboardArrowDown
        )
        SampleButtonOtherUser(
            modifier = Modifier
                .defaultMinSize(minWidth)
                .height(height),
            text = "Message",
            icon = Icons.Default.KeyboardArrowDown
        )
        SampleButtonOtherUser(
            modifier = Modifier
                .defaultMinSize(minWidth)
                .height(height),
            text = "Email",
            icon = Icons.Default.KeyboardArrowDown
        )
        SampleButtonOtherUser(
            modifier = Modifier
                .size(height),
            icon = Icons.Default.KeyboardArrowDown
        )
    }

}

@Composable
fun SampleButtonOtherUser(modifier: Modifier = Modifier, text: String? = null, icon: ImageVector? = null) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(Color(0xFFE2E1E1))
            .clickable {
            }
    ) {
        if (text != null) {
            Text(text = text, fontWeight = FontWeight.SemiBold, overflow = TextOverflow.Ellipsis)
        }
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = "", tint = Color.Black)
        }
    }
}

@Composable
fun BioSectionOtherUser(name: String, activityLabel: String, descriptoion: String, link: String, followers: AnnotatedString) {
    val letterSpacing = 0.5.sp
    val lineHeight = 20.sp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(text = name, fontWeight = FontWeight.Bold, letterSpacing = letterSpacing, lineHeight = lineHeight)
        Text(text = activityLabel, fontWeight = FontWeight.Medium, letterSpacing = letterSpacing, lineHeight = lineHeight, color = Color.Gray)
        Text(text = descriptoion, fontWeight = FontWeight.Medium, letterSpacing = letterSpacing, lineHeight = lineHeight)
        Text(text = link, fontWeight = FontWeight.Medium, letterSpacing = letterSpacing, lineHeight = lineHeight, color = Color(0xFF174A72))
        Text(text = followers, fontWeight = FontWeight.Medium, letterSpacing = letterSpacing, lineHeight = lineHeight, fontSize = 13.sp)
    }
}


@Composable
fun FollowStatusBarOtherUser(modifier: Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        FollowSectoionOtherUser(number = " 3", label = "Posts", modifier = Modifier)
        FollowSectoionOtherUser(number = " 49K", label = "Follower", modifier = Modifier)
        FollowSectoionOtherUser(number = " 13", label = "Following", modifier = Modifier)
    }
}

@Composable
fun FollowSectoionOtherUser(number: String, label: String, modifier: Modifier.Companion) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = number, fontWeight = FontWeight.Bold, fontSize = 20.sp )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun ImageBuilderOtherUser(image: Painter, modifier: Modifier) {

    Image(
        painter = image,
        contentDescription = "",
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(width = 2.dp, color = Color.LightGray, shape = CircleShape)
            .padding(5.dp)
            .clip(CircleShape)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarOtherUser(navController: NavController, modifier: Modifier, viewModel: ProfileOtherUserViewModel = hiltViewModel()) {

    var expanded by remember { mutableStateOf(false) }

    val user = viewModel.user

    TopAppBar(title = {
        Text(
            text = user?.username ?: "Unknown User",
            modifier.padding(start = 20.dp, bottom = 6.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back", modifier.size(30.dp))
            }
        },
        actions = {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = "Notifications",
                            modifier = Modifier.size(30.dp),
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    )
}