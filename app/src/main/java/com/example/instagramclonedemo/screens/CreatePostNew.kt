package com.example.instagramclonedemo.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.instagramclonedemo.R
import com.example.instagramclonedemo.data.CreateUserDto
import com.example.instagramclonedemo.presentation.viewModel.CreatePostNewViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


private var currentUser: CreateUserDto? = null
@ExperimentalFoundationApi
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreatePostNew(viewModel: CreatePostNewViewModel = hiltViewModel(), onPostCreated: () -> Unit){

    val db = FirebaseFirestore.getInstance()
    var username by remember { mutableStateOf("") }
    var profile by remember { mutableStateOf("") }

    db.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid)
        .get().addOnCompleteListener { task: Task<DocumentSnapshot?> ->
            if (task.isSuccessful && task.result != null) {
                profile = task.result!!.getString("imageUrl").toString()
                username = task.result!!.getString("username").toString()
                //other stuff
            } else {
                //deal with error
            }
        }

    var content by remember { mutableStateOf(TextFieldValue("")) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var postImageList by remember { mutableStateOf(listOf<Int>()) }
    var likedBy by remember { mutableStateOf(listOf<CreateUserDto>()) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = rememberImagePainter(data = profile, builder = {
                    transformations(CircleCropTransformation())
                }),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )
            Column {
                Text(text = username, style = MaterialTheme.typography.subtitle1)
                Text(text = "Public", style = MaterialTheme.typography.subtitle2)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = content,
            onValueChange = { content = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
                .background(Color.LightGray),
            textStyle = TextStyle(fontSize = 16.sp),
            decorationBox = { innerTextField ->
                if (content.text.isEmpty()) {
                    Text(
                        text = "Write something...",
                        style = TextStyle(color = Color.Gray, fontSize = 16.sp)
                    )
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { launcher.launch("image/*") }) {
            Text("Select Image/Video")
        }

        Spacer(modifier = Modifier.height(16.dp))

        imageUri?.let {
            Image(
                painter = rememberImagePainter(it),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.createPost(
                content = content.text,
                mediaUri = imageUri,
                profile = profile,
                username = username,
                postImageList = postImageList,
                likedBy = likedBy,
                onSuccess = {
                    onPostCreated()
                },
                onFailure = { e ->
                    // Handle error
                    e.printStackTrace()
                }
            )
        }) {
            Text("Post")
        }
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


//@OptIn(ExperimentalFoundationApi::class)
//@Preview(showBackground = true)
//@Composable
//fun AddPostView(){
//    CreatePostNew()
//}