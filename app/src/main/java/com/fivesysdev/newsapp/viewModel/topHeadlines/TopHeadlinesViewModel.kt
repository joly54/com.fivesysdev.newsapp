package com.fivesysdev.newsapp.viewModel.topHeadlines

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.fivesysdev.newsapp.adapters.NewsAdapter
import com.fivesysdev.newsapp.data.ApiService
import com.fivesysdev.newsapp.model.topHeadlines.Article
import kotlinx.coroutines.launch

class TopHeadlinesViewModel(
    private val apiService: ApiService,
    private val adapter: NewsAdapter,
) : ViewModel() {
    private val _state = MutableLiveData<States>()
    val ModelLis: MutableLiveData<MutableList<Article>> by lazy {
        MutableLiveData<MutableList<Article>>()
    }

    val stateObserver = Observer<States> {
        if (it is States.Success) {
            adapter.setTopHeadlines(it.data)
            println("Update List")
        }
    }

    val state: MutableLiveData<States>
        get() = _state

    init {
        getTopHeadlines()
    }

    fun getTopHeadlines() {
        viewModelScope.launch {
            _state.value = States.Loading
            try {
                val response = apiService.getTopHeadlines()
                _state.value = States.Success(response)
            } catch (e: Exception) {
                println("Error")
                _state.value = States.Error(e.message.toString())
            }
        }
    }
}