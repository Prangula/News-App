package com.example.newsapp


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {
    @GET("top-headlines")
    fun getNews(
        @Query("country") country: String,
        @Query("category") categoty:String,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey")apiKey:String
    ): Call<NewsResponse>
}