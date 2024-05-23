package com.example.instagramclonedemo.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.instagramclonedemo.R
import com.example.instagramclonedemo.model.Post
import com.example.instagramclonedemo.model.Stories
import com.example.instagramclonedemo.model.User
import com.example.instagramclonedemo.updateBottomNavBarVisibility
import com.example.instagramclonedemo.util.Screens
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator


@Composable
fun HomeScreen(navController: NavController) {
    updateBottomNavBarVisibility(true)
    Column(modifier = Modifier.fillMaxSize()) {
        AppToolBar(navController)
        StoriesSection(storyList = getStories())
        Divider(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .height(2.dp))
        PostSection(Modifier.fillMaxWidth(), getPostData())
    }
}




@Composable
fun AppToolBar(navController: NavController) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)){
        Image(
            painter = painterResource(id = R.drawable.instagram_logo),
            contentDescription = "LogoInstagram",
            modifier = Modifier
                .width(150.dp)
                .height(50.dp)
                .align(Alignment.TopStart))

        Row(modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(7.5.dp)) {
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(onClick = {
                navController.navigate(Screens.MessagesListScreenDemo.route)
            },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.heart),
                    contentDescription = "CreatePost",
                    modifier = Modifier.size(30.dp)
                )
            }
//            Icon(painter = painterResource(id = R.drawable.heart),
//                contentDescription = "CreatePost",
//                modifier = Modifier.size(30.dp)
//            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(onClick = {
                navController.navigate(Screens.MessagesListScreen.route)
            },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.messenger),
                    contentDescription = "CreatePost",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Composable
fun StoriesSection(modifier: Modifier = Modifier, storyList: List<Stories>) {
    LazyRow(modifier = modifier) {
        items(storyList) { story ->
            StoryItem(modifier = Modifier, story = story)
        }
    }
}

@Composable
fun StoryItem(modifier: Modifier, story: Stories) {
    Column(modifier = Modifier
        .padding(5.dp)
        .height(84.dp)) {
        Image(
            painter = painterResource(id = story.profile),
            contentDescription = "Story Profile",
            modifier = Modifier
                .size(60.dp)
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xFFDE0046),
                            Color(0xFFF7A34B)
                        )
                    ),
                    shape = CircleShape
                )
                .padding(5.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = story.userName,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.height(60.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

fun getStories(): List<Stories> = listOf(
    Stories(userName = "Your Story", profile = R.drawable.profile_tbq),
    Stories(userName = "hen_du", profile = R.drawable.allena1),
    Stories(userName = "sary_young", profile = R.drawable.dog),
    Stories(userName = "miss_levi", profile = R.drawable.helen),
    Stories(userName = "dev_nguyen", profile = R.drawable.jiro),
    Stories(userName = "do_mixi", profile = R.drawable.hello),
    Stories(userName = "rambo_refund", profile = R.drawable.aleena),
)

@Composable
fun PostSection(modifier: Modifier, postList: List<Post>) {
    LazyColumn{
        items(postList){post ->
            PostItem(modifier = modifier, post = post)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun PostItem(modifier: Modifier, post: Post) {
    val pagerState = rememberPagerState(pageCount = {
        post.postImageList.size
    })
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 10.dp)) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)){

            Row(modifier = Modifier.align(Alignment.CenterStart), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = post.profile),
                    contentDescription = "profile",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = post.userName,
                    fontSize = 12.sp, maxLines = 1,
                    modifier = Modifier.width(100.dp),
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )

            }

            Icon(
                painter = painterResource(id = R.drawable.more),
                contentDescription = "More",
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 10.dp)
                    .align(Alignment.CenterEnd)
            )
        }

        PostCarousel(post.postImageList, pagerState)

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp))    {
            Row(modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(painter = painterResource(id = R.drawable.heart),
                    contentDescription = "Like",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(painter = painterResource(id = R.drawable.messenger),
                    contentDescription = "Messenger",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(painter = painterResource(id = R.drawable.share),
                    contentDescription = "Send",
                    modifier = Modifier.size(24.dp)
                )
            }

            if(pagerState.pageCount > 1){
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    pageCount = post.postImageList.size,
                    activeColor = Color(0xFF00D2EC),
                    inactiveColor = Color(0xFFD6CFCF),
                    modifier = Modifier.align(Alignment.Center)
                )
            }



            Icon(painter = painterResource(id = R.drawable.bookmark),
                contentDescription = "Send",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterEnd)
            )
        }

        LikeSection(post.likedBy)

        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(Color.Black, fontWeight = FontWeight.Bold)){
                append("${post.userName}  ")
            }
            append(post.description)
        }

        Text(
            text = annotatedString,
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier.padding(horizontal = 10.dp)
        )

    }
}

@Composable
fun LikeSection(likedBy: List<User>) {
    if(likedBy.size > 10){
        Text(text = "${likedBy.size}",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }else if(likedBy.size == 1){
        Text(text = "Like by${likedBy[0].userName}",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }else{
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 10.dp)) {
            PostLikeViewByProfile(likedBy)
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "liked by ${likedBy[0].userName} and ${likedBy.size - 1} others",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun PostLikeViewByProfile(likedBy: List<User>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy((-5).dp)){
        items(likedBy.take(4)) {likesBy ->
            Image(
                painter = painterResource(id = likesBy.profile),
                contentDescription = "Story Profile",
                modifier = Modifier
                    .size(24.dp)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostCarousel(postImageList: List<Int>, pagerState: PagerState) {

    Box(modifier = Modifier.fillMaxWidth()){

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth())
        { currentPage ->
            Image(
                painter = painterResource(id = postImageList[currentPage]),
                contentDescription = "Post Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(375.dp),
                contentScale = ContentScale.Crop
            )
        }

        if(pagerState.pageCount > 1){
            PostNudgeCount(modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(vertical = 10.dp, horizontal = 15.dp), pagerState)
        }
    }



}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostNudgeCount(modifier: Modifier = Modifier,pagerState: PagerState){
    Row(modifier = modifier
        .clip(CircleShape)
        .background(Color.Black.copy(0.6f))
        .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(text = (pagerState.currentPage + 1).toString(), color = Color.White)
        Text(text = "/", color = Color.White)
        Text(text = pagerState.pageCount.toString(), color = Color.White)
    }
}

fun getPostData(): List<Post> = listOf(
    Post(
        userName = "Aleena",
        profile = R.drawable.aleena,
        postImageList = listOf(R.drawable.aleena, R.drawable.helen, R.drawable.allena1),
        description = "I happy with my friends",
        likedBy = listOf(
            User(profile = R.drawable.aleena, userName = "aleena"),
            User(profile = R.drawable.allena1, userName = "hen_du"),
            User(profile = R.drawable.dog, userName = "sary_young"),
            User(profile = R.drawable.helen, userName = "miss_levi"),
            User(profile = R.drawable.jiro, userName = "dev_nguyen")
        )
    ),
    Post(
        userName = "Bear",
        profile = R.drawable.helen,
        postImageList = listOf(R.drawable.helen, R.drawable.helen, R.drawable.allena1),
        description = "I happy with my friends",
        likedBy = listOf(
            User(profile = R.drawable.aleena, userName = "aleena"),
            User(profile = R.drawable.allena1, userName = "hen_du"),
            User(profile = R.drawable.dog, userName = "sary_young"),
            User(profile = R.drawable.helen, userName = "miss_levi"),
            User(profile = R.drawable.jiro, userName = "dev_nguyen")
        )
    ),
    Post(
        userName = "Linda",
        profile = R.drawable.allena1,
        postImageList = listOf(R.drawable.allena1, R.drawable.dog, R.drawable.aleena),
        description = "I happy with my friends",
        likedBy = listOf(
            User(profile = R.drawable.aleena, userName = "aleena"),
            User(profile = R.drawable.allena1, userName = "hen_du"),
            User(profile = R.drawable.dog, userName = "sary_young"),
            User(profile = R.drawable.helen, userName = "miss_levi"),
            User(profile = R.drawable.jiro, userName = "dev_nguyen")
        )
    )
)



@Preview(showBackground = true)
@Composable
fun InstagramProfilePreview(){
    StoryItem(Modifier, Stories())
}

@Preview(showBackground = true)
@Composable
fun InstagramPostItemPreview(){
    PostItem(Modifier, post = Post(
        userName = "Aleena",
        profile = R.drawable.aleena,
        postImageList = listOf(R.drawable.aleena),
        description = "I happy with my friends",
        likedBy = listOf(
            User(profile = R.drawable.aleena, userName = "aleena"),
            User(profile = R.drawable.allena1, userName = "hen_du"),
            User(profile = R.drawable.dog, userName = "sary_young"),
            User(profile = R.drawable.helen, userName = "miss_levi"),
            User(profile = R.drawable.jiro, userName = "dev_nguyen")
        )
    )
    )
}

