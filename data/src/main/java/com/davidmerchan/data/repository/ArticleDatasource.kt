package com.davidmerchan.data.repository

import com.davidmerchan.data.datasource.ArticlesLocalDatasource
import com.davidmerchan.data.datasource.ArticlesRemoteDataSource
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleDatasourceRepository
import java.io.IOException
import javax.inject.Inject

class ArticleDatasource @Inject constructor(
    private val localDatasource: ArticlesLocalDatasource,
    private val remoteDataSource: ArticlesRemoteDataSource
) : ArticleDatasourceRepository {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun getArticles(): Resource<List<Article>> {
        val connected = true
        return try {
            val result = if (connected) {
                remoteDataSource.getArticles()
            } else {
                localDatasource.getArticles()
            }
            Resource.Success(result)
        } catch (io: IOException) {
            Resource.Error(io)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}
