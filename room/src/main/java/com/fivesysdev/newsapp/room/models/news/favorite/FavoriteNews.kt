package com.fivesysdev.newsapp.room.models.news.favorite

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.fivesysdev.newsapp.room.models.news.news.News

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = News::class,
            parentColumns = ["id"],
            childColumns = ["news_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FavoriteNews(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val news_id: Int,
)