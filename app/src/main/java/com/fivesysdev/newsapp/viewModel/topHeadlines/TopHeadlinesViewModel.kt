package com.fivesysdev.newsapp.viewModel.topHeadlines

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fivesysdev.newsapp.adapters.NewsAdapter
import com.fivesysdev.newsapp.data.ApiService
import com.fivesysdev.newsapp.model.topHeadlines.Article
import com.fivesysdev.newsapp.room.DataBaseViewModel
import com.fivesysdev.newsapp.room.models.news.news.News
import kotlinx.coroutines.launch

class TopHeadlinesViewModel(
    private val apiService: ApiService,
    private val adapter: NewsAdapter,
    private val application: Application
) : ViewModel() {
    private val _state = MutableLiveData<States>()
    private val dbViewModel = DataBaseViewModel(application)
    val ModelLis: MutableLiveData<MutableList<Article>> by lazy {
        MutableLiveData<MutableList<Article>>()
    }

    val stateObserver = Observer<States> {
        if (it is States.Success) {
            adapter.setTopHeadlines(it.data)
        }
    }

    val state: MutableLiveData<States>
        get() = _state

    init {
        getTopHeadlines()
    }

    fun getTopHeadlines() {
        println("getTopHeadlines")
        viewModelScope.launch {
            _state.value = States.Loading
            try {
                val response = apiService.getTopHeadlines()
                _state.value = States.Success(response)
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
                _state.value = States.Error(e.message.toString())
            }
        }
    }
}