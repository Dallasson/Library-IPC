package com.app.ipc_library.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceholderApi {
    @GET("posts")
    fun getAllPosts(): Call<List<Post>>
}