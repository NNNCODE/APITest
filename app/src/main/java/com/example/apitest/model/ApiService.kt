package com.example.apitest.model

import com.example.apitest.api.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Post
}