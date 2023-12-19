package com.fivesysdev.newsapp.viewModel.topHeadlines

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fivesysdev.newsapp.data.ApiService
import com.fivesysdev.newsapp.model.topHeadlines.Article
import kotlinx.coroutines.launch

class TopHeadlinesViewModel(
    private val apiService: ApiService
): ViewModel(){
    private val _state = MutableLiveData<States>()
    val ModelList = mutableListOf<Article>()

    val stateObserver = Observer<States>{
        if(it is States.Success){
            ModelList.addAll(it.data.articles)
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
                println("Success")
            } catch (e: Exception) {
                println("Error")
                _state.value = States.Error(e.message.toString())
            }
        }
    }
}