package com.davidmerchan.data

import com.davidmerchan.data.model.ArticlesResponse
import retrofit2.http.GET

interface ArticleService {
    @GET("search_by_date?query=mobile")
    suspend fun getArticles(): ArticlesResponse
}
