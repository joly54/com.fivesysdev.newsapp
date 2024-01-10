package com.fivesysdev.newsapp.room.models.news.news

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface newsDao {
    @Upsert
    suspend fun upsert(news: News)
    @Delete
    suspend fun delete(news: News)
    @Query("SELECT * FROM news")
    fun getAll(): LiveData<List<News>>
    @Query("DELETE FROM news")
    suspend fun deleteAll()
}