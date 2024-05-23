//package com.example.instagramclonedemo.presentation.viewModel
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//
//class FirebaseViewModel: ViewModel() {
//    var searchContact by mutableStateOf("")
//
//    private val _searchContacts = MutableStateFlow<List<UserData>>(emptyList())
//
//    fun filterContacts(
//        contactList : List<UserData>,
//        toSearch : String
//    ){
//        viewModelScope.launch {
//            val filteredList = contactList.filter {
//                it.username!!.contains(toSearch,true) || it.mail!!.contains(toSearch, true)
//            }
//            _searchContacts.emit(filteredList)
//        }
//    }
//}