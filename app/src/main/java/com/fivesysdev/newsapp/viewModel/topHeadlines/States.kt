package com.fivesysdev.newsapp.viewModel.topHeadlines

import com.fivesysdev.newsapp.model.topHeadlines.TopHeadlines

sealed class States {
    object Loading : States()
    data class Success(val data: TopHeadlines) : States()
    data class Error(val message: String) : States()
}