package com.davidmerchan.domain.repository

import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource

interface ArticleLocalManagerRepository {
    suspend fun saveArticles(articles: List<Article>): Resource<Unit>
    suspend fun deleteArticle(id: Long): Resource<Unit>
    suspend fun restoreArticle(id: Long): Resource<Article>
    suspend fun restoreAllArticles(): Resource<List<Article>>
}
