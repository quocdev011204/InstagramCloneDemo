package com.example.instagramclonedemo.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.instagramclonedemo.R
import com.example.instagramclonedemo.common.components.ImagePickerPermissionChecker
import com.example.instagramclonedemo.util.SharedRef
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreatePostNew(){

    val context = LocalContext.current
    var post by remember{
        mutableStateOf("")
    }

    val darkTheme: Boolean = isSystemInDarkTheme()
    val scaffoldState = rememberScaffoldState()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        val (crossPic, text, logo, userName, editText, attachMedia, replyText, button, imageBox) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.baseline_close_24),
            contentDescription = "Close",
            modifier = Modifier
                .constrainAs(crossPic) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .clickable {

                }
            )

        Text(
            text = "New post",
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp
            ), modifier = Modifier.constrainAs(text){
                top.linkTo(crossPic.top)
                start.linkTo(crossPic.end, margin = 12.dp)
                bottom.linkTo(crossPic.bottom)
            }
        )


        Image(
            painter = rememberAsyncImagePainter(model = SharedRef.getImage(context)),
            contentDescription = "Close",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(text.bottom)
                    start.linkTo(parent.start)
                }
                .size(35.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
            )

        Text(
            text = SharedRef.getUserName(context),
            style = TextStyle(
                fontSize = 20.sp
            ), modifier = Modifier.constrainAs(userName){
                top.linkTo(logo.top)
                start.linkTo(logo.end, margin = 12.dp)
                bottom.linkTo(logo.bottom)
            }
        )

        BasicTextFieldWithHint(hint = "Create a new post...",
            value = post,
            onValueChange = { post = it},
            modifier = Modifier
                .constrainAs(editText) {
                    top.linkTo(userName.bottom)
                    start.linkTo(userName.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.width(30.dp))
        ImageSection(darkTheme, imageUri, coroutineScope, bottomSheetState)
        ImagePickerPermissionChecker(
            coroutineScope,
            bottomSheetState,
            onCameraLaunchResult = { uri ->
                imageUri = uri
            },
            onGalleryLaunchResult = { uri ->
                imageUri = uri
            }
        )
    }


}

@Composable
fun BasicTextFieldWithHint(hint: String, value: String, onValueChange: (String)-> Unit, modifier: Modifier){
    Box(modifier = modifier){
        if(value.isEmpty()){
            Text(text = hint, color = Color.Gray)
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle.Default.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageSection(
    darkTheme: Boolean,
    imageUri: Uri?,
    coroutineScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(5.dp)
    ) {
        if (imageUri == null) {
            Image(
                painter = painterResource(id = R.drawable.baseline_attachment_24),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(30.dp)
                    .clickable {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    }
            )
        } else {
            AsyncImage(
                model = Uri.parse(imageUri.toString()),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clickable {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    }
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun AddPostView(){
    CreatePostNew()
}