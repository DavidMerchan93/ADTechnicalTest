package com.davidmerchan.data.repository

import com.davidmerchan.core.NetworkValidator
import com.davidmerchan.data.datasource.ArticlesLocalDatasource
import com.davidmerchan.data.datasource.ArticlesRemoteDataSource
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleDatasourceRepository
import java.io.IOException
import javax.inject.Inject

class ArticleDatasource @Inject constructor(
    private val localDatasource: ArticlesLocalDatasource,
    private val remoteDataSource: ArticlesRemoteDataSource,
    private val networkValidator: NetworkValidator
) : ArticleDatasourceRepository {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun getArticles(): Resource<List<Article>> {
        return try {
            val result = if (networkValidator.isConnected()) {
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
