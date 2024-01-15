package com.fivesysdev.newsapp.room.models.news.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.fivesysdev.newsapp.room.models.news.news.News

@Dao
interface favoriteNewsDao {
    @Upsert
    suspend fun upsert(favoriteNews: FavoriteNews)
    @Delete
    suspend fun delete(favoriteNews: FavoriteNews)
    @Query("SELECT * FROM favoritenews")
    fun getAll(): LiveData<List<FavoriteNews>>
    @Query("SELECT EXISTS(SELECT * FROM favoritenews WHERE news_id = :news_id)")
    suspend fun isFavorite(news_id: Int): Boolean
    @Query("DELETE FROM favoritenews WHERE news_id = :news_id")
    suspend fun unfavorite(news_id: Int)
    @Query("SELECT * FROM news WHERE id IN (SELECT news_id FROM favoritenews)")
    fun getAllFavorite(): LiveData<List<News>>
}