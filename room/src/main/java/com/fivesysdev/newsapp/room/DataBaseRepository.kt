package com.fivesysdev.newsapp.room

import com.fivesysdev.newsapp.room.models.news.favorite.FavoriteNewsRepo
import com.fivesysdev.newsapp.room.models.news.favorite.favoriteNewsDao
import com.fivesysdev.newsapp.room.models.news.news.NewsRepository
import com.fivesysdev.newsapp.room.models.news.news.newsDao

class DataBaseRepository (
    newsDao: newsDao,
    favoriteNewsDao: favoriteNewsDao
){
    val newsRepository = NewsRepository(newsDao)
    val favoriteNewsRepository = FavoriteNewsRepo(favoriteNewsDao)
}