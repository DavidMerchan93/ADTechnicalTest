package com.davidmerchan.data.repository

import com.davidmerchan.core.network.NetworkValidator
import com.davidmerchan.data.datasource.ArticlesLocalDatasource
import com.davidmerchan.data.datasource.ArticlesRemoteDataSource
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleDatasourceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class ArticleDatasource @Inject constructor(
    private val localDatasource: ArticlesLocalDatasource,
    private val remoteDataSource: ArticlesRemoteDataSource,
    private val networkValidator: NetworkValidator,
    private val ioDispatcher: CoroutineScope
) : ArticleDatasourceRepository {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun getArticles(): Resource<List<Article>> {
        return try {
            val result = withContext(ioDispatcher.coroutineContext) {
                if (networkValidator.isConnected()) {
                    val articles = remoteDataSource.getArticles()
                    localDatasource.saveArticles(articles)
                }

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
