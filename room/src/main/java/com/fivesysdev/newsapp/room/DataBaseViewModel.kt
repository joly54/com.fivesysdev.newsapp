package com.fivesysdev.newsapp.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fivesysdev.newsapp.room.models.news.favorite.FavoriteNews
import com.fivesysdev.newsapp.room.models.news.news.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataBaseViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository: DataBaseRepository

    init {
        val newsDao = NewsDatabase.invoke(application).newsDao()
        val favoriteNewsDao = NewsDatabase.invoke(application).favoriteNewsDao()
        repository = DataBaseRepository(newsDao, favoriteNewsDao)
    }

    val news = NewsActions()

    inner class NewsActions {
        fun getAll() = repository.newsRepository.getAllNews()
        fun getAllFavorite() = repository.favoriteNewsRepository.getAllFavorite()
        fun insert(news: News) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.newsRepository.upsert(news)
            }
        }

        fun delete(news: News) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.newsRepository.delete(news)
            }
        }

        fun replaceAll(list: List<News>) {
            val from_db = repository.newsRepository.getAllNews()
            from_db.observeForever {
                if (from_db.isInitialized) {
                    viewModelScope.launch(Dispatchers.IO) {
                        val inDbButNotInList = from_db.value!!.filter { !list.contains(it) }
                        val inListButNotInDb = list.filter { !from_db.value!!.contains(it) }
                        inDbButNotInList.forEach { repository.newsRepository.delete(it) }
                        inListButNotInDb.forEach { repository.newsRepository.upsert(it) }
                    }
                }
            }
        }
    }

    val favoriteNews = FavoriteNewsActions()

    inner class FavoriteNewsActions {
        fun getAll() = repository.favoriteNewsRepository.getAll()
        fun upsert(favoriteNews: FavoriteNews) {
            viewModelScope.launch(Dispatchers.IO) {
                println("Start upsert")
                repository.favoriteNewsRepository.upsert(favoriteNews)
                println("End upsert")
            }
        }

        fun delete(favoriteNews: FavoriteNews) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.favoriteNewsRepository.delete(favoriteNews)
            }
        }

        fun unFavorite(news_id: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                println("Start unfavorite")
                repository.favoriteNewsRepository.unfavorite(news_id)
                println("End unfavorite")
            }
        }
        fun getAllFavorite() = repository.favoriteNewsRepository.getAllFavorite()

        suspend fun isFavorite(news_id: Int) = repository.favoriteNewsRepository.isFavorite(news_id)
    }
}