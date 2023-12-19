package com.fivesysdev.newsapp.data

import com.fivesysdev.newsapp.model.topHeadlines.TopHeadlines
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "ua",
        @Query("apiKey") key: String = "f77c1e3cd5a948f5afca451b393624b1"
    ): TopHeadlines
}