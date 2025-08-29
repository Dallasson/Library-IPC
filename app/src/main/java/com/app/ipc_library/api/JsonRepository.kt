package com.app.ipc_library.api

class JsonRepository {

    fun fetchAPost(callback: (List<Post>?, Throwable?) -> Unit) {
        RetrofitClient.api.getAllPosts().enqueue(object : retrofit2.Callback<List<Post>> {
            override fun onResponse(call: retrofit2.Call<List<Post>>, response: retrofit2.Response<List<Post>>) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, Throwable("Error: ${response.code()}"))
                }
            }

            override fun onFailure(call: retrofit2.Call<List<Post>>, t: Throwable) {
                callback(null, t)
            }
        })
    }
}