package com.app.ipc_library.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    val api: JsonPlaceholderApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(JsonPlaceholderApi::class.java)
    }
}