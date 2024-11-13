package com.davidmerchan.data.datasource

import com.davidmerchan.data.model.ArticlesResponse
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.data.ArticleService
import javax.inject.Inject

class ArticlesRemoteDataSource @Inject constructor(
    private val articleService: ArticleService
) {
    suspend fun getArticles(): List<Article> {
        val articles = articleService.getArticles()
        return articles.mapToDomain()
    }
}