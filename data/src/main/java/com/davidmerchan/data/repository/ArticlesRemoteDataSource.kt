package com.davidmerchan.data.repository

import com.davidmerchan.data.model.ArticlesResponse
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.repository.ArticleDatasourceRepository
import com.davidmerchan.network.ArticleService
import javax.inject.Inject

class ArticlesRemoteDataSource @Inject constructor(
    private val articleService: ArticleService
): ArticleDatasourceRepository {
    override fun getArticles(): List<Article> {
        val articles = articleService.getArticles<ArticlesResponse>()
        return articles.mapToDomain()
    }
}