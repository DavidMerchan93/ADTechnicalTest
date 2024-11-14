package com.davidmerchan.domain.repository

import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource

interface ArticleLocalManagerRepository {
    fun saveArticles(articles: List<Article>): Resource<Unit>
    fun deleteArticle(id: Long): Resource<Unit>
    fun restoreArticle(id: Long): Resource<Article>
    fun restoreAllArticles(): Resource<List<Article>>
}
