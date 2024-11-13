package com.davidmerchan.data.datasource

import com.davidmerchan.data.mapper.mapToDomain
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.network.api.ArticleService
import javax.inject.Inject

interface ArticlesRemoteDataSource {
    suspend fun getArticles(): List<Article>
}

class ArticlesRetrofitDataSource @Inject constructor(
    private val articleService: ArticleService
): ArticlesRemoteDataSource {
    override suspend fun getArticles(): List<Article> {
        val articles = articleService.getArticles()
        return articles.mapToDomain()
    }
}
