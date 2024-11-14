package com.davidmerchan.data.datasource

import com.davidmerchan.data.mapper.mapToDomain
import com.davidmerchan.data.mapper.mapToEntity
import com.davidmerchan.database.dao.ArticleDao
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.ArticleId
import javax.inject.Inject

interface ArticlesLocalDatasource {
    fun getArticles(): List<Article>
    fun saveArticles(articles: List<Article>)
    fun deleteArticle(id: Long)
    fun restoreArticle(id: Long): Article
    fun restoreAllArticles(): List<Article>
}

class ArticlesRoomDatasource @Inject constructor(
    private val articleDao: ArticleDao
) : ArticlesLocalDatasource {
    override fun getArticles(): List<Article> {
        val articles = articleDao.getArticles()
        return articles.map { it.mapToDomain() }
    }

    @Suppress("SpreadOperator")
    override fun saveArticles(articles: List<Article>) {
        val data = articles.map { it.mapToEntity() }.toTypedArray()
        articleDao.insertArticles(*data)
    }

    override fun deleteArticle(id: ArticleId) {
        articleDao.deleteArticle(id)
    }

    override fun restoreArticle(id: Long): Article {
        val article = articleDao.restore(id)
        return article.mapToDomain()
    }

    override fun restoreAllArticles(): List<Article> {
        val article = articleDao.restoreAll()
        return article.map { it.mapToDomain() }
    }
}
