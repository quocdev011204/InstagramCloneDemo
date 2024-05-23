//package com.example.instagramclonedemo.presentation.chat
//
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.OutlinedTextField
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.instagramclonedemo.presentation.viewModel.FirebaseViewModel
//import com.example.instagramclonedemo.presentation.viewModel.TaskViewModel
//
//@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
//@Composable
//fun chatScreen(
//    taskViewModel: TaskViewModel,
//    firebaseViewModel: FirebaseViewModel,
//    navController: NavController
//){
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .statusBarsPadding()
//            .navigationBarsPadding(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ){
//
//        Row (
//            modifier = Modifier.fillMaxWidth(0.9f),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ){
//            Text(
//                text = "Chats",
//                fontSize = 35.sp,
//                fontWeight = FontWeight.Bold,
//                color = MaterialTheme.colorScheme.primary,
//            )
//        }
//
//        Spacer(modifier = Modifier.size(5.dp))
//
//        OutlinedTextField(
//            value = firebaseViewModel.searchContact,
//            onValueChange = {
//                    newValue -> firebaseViewModel.searchContact = newValue
//                firebaseViewModel.filterContacts(firebaseViewModel.chatListUsers.value,firebaseViewModel.searchContact)
//            },
//            label = { Text("Search") },
////            placeholder = { Text(text = "Search By Name or Email")},
////            leadingIcon = {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Spacer(modifier = Modifier.size(30.dp))
//                    Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
//                    Spacer(modifier = Modifier.size(10.dp))
//                }
//            },
//            keyboardOptions = KeyboardOptions.Default.copy(
//                imeAction = ImeAction.Search,
//            ),
//            modifier = Modifier
//                .fillMaxWidth(0.9f)
//                .padding(bottom = 16.dp),
//            shape = RoundedCornerShape(30.dp),
//            singleLine = true,
//            colors = OutlinedTextFieldDefaults.colors(
//                unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(0.2f),
//                unfocusedBorderColor = Color.Transparent,
//                focusedContainerColor = MaterialTheme.colorScheme.primary.copy(0.2f),
//            )
//        )
//
//    }
//}