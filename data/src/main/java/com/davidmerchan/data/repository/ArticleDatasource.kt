package com.davidmerchan.data.repository

import com.davidmerchan.data.datasource.ArticlesLocalDatasource
import com.davidmerchan.data.datasource.ArticlesRemoteDataSource
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.repository.ArticleDatasourceRepository
import javax.inject.Inject

class ArticleDatasource @Inject constructor(
    private val localDatasource: ArticlesLocalDatasource,
    private val remoteDataSource: ArticlesRemoteDataSource
) : ArticleDatasourceRepository {
    override suspend fun getArticles(): List<Article> {
        val connected = true
        return if (connected) {
            val remoteArticles = remoteDataSource.getArticles()
            localDatasource.saveArticles(remoteArticles)
            remoteArticles
        } else {
            localDatasource.getArticles()
        }
    }
}
