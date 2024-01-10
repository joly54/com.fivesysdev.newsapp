package com.fivesysdev.newsapp.room.models.news.news

class NewsRepository(private val newsDao: newsDao){
    suspend fun upsert(news: News) = newsDao.upsert(news)
    suspend fun delete(news: News) = newsDao.delete(news)
    suspend fun deleteAll() = newsDao.deleteAll()
    fun getAllNews() = newsDao.getAll()
}