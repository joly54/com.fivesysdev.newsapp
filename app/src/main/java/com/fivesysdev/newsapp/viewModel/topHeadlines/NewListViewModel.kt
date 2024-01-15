package com.fivesysdev.newsapp.viewModel.topHeadlines

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fivesysdev.newsapp.data.ApiService
import com.fivesysdev.newsapp.model.topHeadlines.Article
import com.fivesysdev.newsapp.room.DataBaseViewModel
import com.fivesysdev.newsapp.room.models.news.news.News
import kotlinx.coroutines.launch

class NewListViewModel(
    private val apiService: ApiService,
    application: Application
) : ViewModel() {
    private val dbViewModel = DataBaseViewModel(application)
    val ModelLis: MutableLiveData<MutableList<Article>> by lazy {
        MutableLiveData<MutableList<Article>>()
    }
    fun getTopHeadlines() {
        println("getTopHeadlines")
        viewModelScope.launch {
            try {
                val response = apiService.getTopHeadlines()
                dbViewModel.news.replaceAll(
                    response.articles.map {
                        News(
                            title = it.title?: "",
                            description = it.description?: "",
                            urlToImage = it.urlToImage?: "",
                            url = it.url?: "",
                            id = it.url.hashCode()
                        )
                    }
                )
                println("Success")
            } catch (e: Exception) {
                println("Error")
                e.printStackTrace()
            }
        }
    }
}