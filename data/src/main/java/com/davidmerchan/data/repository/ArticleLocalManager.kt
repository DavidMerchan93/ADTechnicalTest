package com.davidmerchan.data.repository

import com.davidmerchan.data.datasource.ArticlesLocalDatasource
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleLocalManagerRepository
import javax.inject.Inject

class ArticleLocalManager @Inject constructor(
    private val articlesLocalDatasource: ArticlesLocalDatasource
) : ArticleLocalManagerRepository {

    @Suppress("TooGenericExceptionCaught")
    override fun saveArticles(articles: List<Article>): Resource<Unit> {
        return try {
            articlesLocalDatasource.saveArticles(articles)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override fun deleteArticle(id: Long): Resource<Unit> {
        return try {
            articlesLocalDatasource.deleteArticle(id)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override fun restoreArticle(id: Long): Resource<Article> {
        return try {
            val article = articlesLocalDatasource.restoreArticle(id)
            Resource.Success(article)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override fun restoreAllArticles(): Resource<List<Article>> {
        return try {
            val articles = articlesLocalDatasource.restoreAllArticles()
            Resource.Success(articles)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}
