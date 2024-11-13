package com.davidmerchan.data.datasource

import com.davidmerchan.data.mapper.mapToDomain
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.network.api.ArticleService
import javax.inject.Inject

class ArticlesRemoteDataSource @Inject constructor(
    private val articleService: ArticleService
) {
    suspend fun getArticles(): List<Article> {
        val articles = articleService.getArticles()
        return articles.mapToDomain()
    }
}
