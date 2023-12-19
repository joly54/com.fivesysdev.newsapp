package com.fivesysdev.newsapp.di

import com.fivesysdev.newsapp.data.ApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private val baseUrl = "https://newsapi.org/v2/"
    private val clinet = OkHttpClient.Builder().build()
    val module = module {
        single {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(clinet)
                .build()
                .create(ApiService::class.java)
        }
    }
}