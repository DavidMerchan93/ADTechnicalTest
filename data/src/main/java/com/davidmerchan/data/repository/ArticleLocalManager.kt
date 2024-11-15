package com.davidmerchan.data.repository

import com.davidmerchan.data.datasource.ArticlesLocalDatasource
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleLocalManagerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleLocalManager @Inject constructor(
    private val articlesLocalDatasource: ArticlesLocalDatasource,
    private val ioDispatcher: CoroutineScope
) : ArticleLocalManagerRepository {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun saveArticles(articles: List<Article>): Resource<Unit> {
        return try {
            withContext(ioDispatcher.coroutineContext) {
                articlesLocalDatasource.saveArticles(articles)
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override suspend fun deleteArticle(id: Long): Resource<Unit> {
        return try {
            withContext(ioDispatcher.coroutineContext) {
                articlesLocalDatasource.deleteArticle(id)
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override suspend fun restoreArticle(id: Long): Resource<Article> {
        return try {
            val article = withContext(ioDispatcher.coroutineContext) {
                articlesLocalDatasource.restoreArticle(id)
            }
            Resource.Success(article)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override suspend fun restoreAllArticles(): Resource<List<Article>> {
        return try {
            val articles = withContext(ioDispatcher.coroutineContext) {
                articlesLocalDatasource.restoreAllArticles()
            }
            Resource.Success(articles)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}
