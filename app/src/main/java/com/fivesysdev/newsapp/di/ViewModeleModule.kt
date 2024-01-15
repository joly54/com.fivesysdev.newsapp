package com.fivesysdev.newsapp.di

object ViewModeleModule {
    val module = org.koin.dsl.module{
        single {
            com.fivesysdev.newsapp.viewModel.topHeadlines.NewListViewModel(
                apiService = get(),
                application = get()
            )
        }
    }
}