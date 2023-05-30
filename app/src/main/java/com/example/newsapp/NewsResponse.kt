package com.example.newsapp

data class NewsResponse(

    val status:String,
    val totalResult:String,
    val articles:List<Articles>

):java.io.Serializable
