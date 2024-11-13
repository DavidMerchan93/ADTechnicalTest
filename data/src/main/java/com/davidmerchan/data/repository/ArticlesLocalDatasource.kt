package com.davidmerchan.data.repository

import com.davidmerchan.data.local.ArticleDao
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.repository.ArticleDatasourceRepository
import javax.inject.Inject

class ArticlesLocalDatasource @Inject constructor(
    private val articleDao: ArticleDao
): ArticleDatasourceRepository {
    override fun getArticles(): List<Article> {
        val articles = articleDao.getArticles()
        return articles.map { it.mapToDomain() }
    }
}