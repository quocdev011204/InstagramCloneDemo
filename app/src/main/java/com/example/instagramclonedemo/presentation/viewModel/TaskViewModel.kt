package com.example.instagramclonedemo.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TaskViewModel: ViewModel() {
    var showImageDialog by mutableStateOf(false)
    var isForwarding by mutableStateOf(false)
    var showDialog by mutableStateOf(false)
    var showDeleteMsgDialog by mutableStateOf(false)
    var showVideoDialog by mutableStateOf(false)
    var showSetProfilePictureAndStatusDialog by mutableStateOf(false)
    var chatOptions by mutableStateOf(false)
    var isEditing by mutableStateOf(false)
    var isUploadingStatus by mutableStateOf(false)
    var showDeleteStatusOption by mutableStateOf(false)
    var allEmojis by mutableStateOf(false)
    var isStatus by mutableStateOf(true)
    var showStoryViewers by mutableStateOf(false)
    var searchMessages by mutableStateOf(false)
    var selectMedia by mutableStateOf(false)
    var expandUploadQualitySetting by mutableStateOf(false)
    var expandedPersonInfo by mutableStateOf(false)

    fun getTime(mills: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = mills

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}