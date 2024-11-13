package com.davidmerchan.network

import retrofit2.http.GET

interface ArticleService {
    @GET("")
    fun <T> getArticles(): T
}
