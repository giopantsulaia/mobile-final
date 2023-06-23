package com.example.finalproject.dataclasses

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val title: String,
    val url: String,
    val published_date: String,
    val publisher: Publisher
)

data class Publisher(
    val name: String,
    val url: String
)