package com.davidmerchan.data.datasource

import com.davidmerchan.data.local.ArticleDao
import com.davidmerchan.data.local.ArticleEntity
import com.davidmerchan.data.local.mapToEntity
import com.davidmerchan.domain.entitie.Article
import javax.inject.Inject

class ArticlesLocalDatasource @Inject constructor(
    private val articleDao: ArticleDao
) {
    fun getArticles(): List<Article> {
        val articles = articleDao.getArticles()
        return articles.map { it.mapToDomain() }
    }

    fun saveArticles(articles: List<Article>) {
        val data: Array<ArticleEntity> = articles.map { it.mapToEntity() }.toTypedArray()
        articleDao.insertArticles(*data)
    }

    fun deleteArticle(id: Long) {
        println("id: $id")
    }
}