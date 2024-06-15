package com.example.instagramclonedemo.domain.model

data class UploadStatus(
    val progress: Float? = 0f,
    val MegabytesTransferred: String? = "",
    val totalMegaBytes: String? = ""
)