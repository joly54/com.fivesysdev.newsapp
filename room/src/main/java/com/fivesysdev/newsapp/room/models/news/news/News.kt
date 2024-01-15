package com.fivesysdev.newsapp.room.models.news.news

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News (
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
)