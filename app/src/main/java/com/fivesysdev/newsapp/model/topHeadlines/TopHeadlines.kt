package com.fivesysdev.newsapp.model.topHeadlines

data class TopHeadlines(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)