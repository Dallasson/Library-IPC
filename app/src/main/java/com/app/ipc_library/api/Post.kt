package com.app.ipc_library.api

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)