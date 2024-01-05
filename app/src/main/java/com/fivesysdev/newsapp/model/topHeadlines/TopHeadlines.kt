package com.fivesysdev.newsapp.model.topHeadlines

data class TopHeadlines(
    val articles: List<Article> = listOf(),
    val status: String = "",
    val totalResults: Int = 0
)