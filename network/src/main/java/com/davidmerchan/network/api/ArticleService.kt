package com.davidmerchan.network.api

import com.davidmerchan.network.model.ArticlesResponse
import retrofit2.http.GET

interface ArticleService {
    @GET("search_by_date?query=mobile")
    suspend fun getArticles(): ArticlesResponse
}
