package com.davidmerchan.data.datasource

import com.davidmerchan.data.mapper.mapToDomain
import com.davidmerchan.data.mapper.mapToEntity
import com.davidmerchan.database.entity.ArticleEntity
import com.davidmerchan.database.dao.ArticleDao
import com.davidmerchan.domain.entitie.Article
import javax.inject.Inject

interface ArticlesLocalDatasource {
    fun getArticles(): List<Article>
    fun saveArticles(articles: List<Article>)
    fun deleteArticle(id: Long)
}

class ArticlesRoomDatasource @Inject constructor(
    private val articleDao: ArticleDao
): ArticlesLocalDatasource {
    override fun getArticles(): List<Article> {
        val articles = articleDao.getArticles()
        return articles.map { it.mapToDomain() }
    }

    @Suppress("SpreadOperator")
    override fun saveArticles(articles: List<Article>) {
        val data: Array<ArticleEntity> = articles.map { it.mapToEntity() }.toTypedArray()
        articleDao.insertArticles(*data)
    }

    override fun deleteArticle(id: Long) {
        println("id: $id")
    }
}
