package com.davidmerchan.network

import retrofit2.http.GET

interface ArticleService {
    @GET("/search_by_date?query=mobile")
    suspend fun <T> getArticles(): T
}
