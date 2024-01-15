package com.fivesysdev.newsapp.room.models.news.favorite


class FavoriteNewsRepo(private val favoriteNewsDao: favoriteNewsDao){
    suspend fun upsert(favoriteNews: FavoriteNews) = favoriteNewsDao.upsert(favoriteNews)
    suspend fun delete(favoriteNews: FavoriteNews) = favoriteNewsDao.delete(favoriteNews)
    fun getAll() = favoriteNewsDao.getAll()
    suspend fun isFavorite(news_id: Int) = favoriteNewsDao.isFavorite(news_id)
    suspend fun unfavorite(news_id: Int) = favoriteNewsDao.unfavorite(news_id)
    fun getAllFavorite() = favoriteNewsDao.getAllFavorite()
}