package com.davidmerchan.di

import com.davidmerchan.data.datasource.ArticlesLocalDatasource
import com.davidmerchan.data.datasource.ArticlesRemoteDataSource
import com.davidmerchan.data.datasource.ArticlesRetrofitDataSource
import com.davidmerchan.data.datasource.ArticlesRoomDatasource
import com.davidmerchan.database.dao.ArticleDao
import com.davidmerchan.data.repository.ArticleDatasource
import com.davidmerchan.domain.repository.ArticleDatasourceRepository
import com.davidmerchan.network.api.ArticleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ArticlesModule {

    @Provides
    fun provideArticlesRemoteDataSource(
        articleService: ArticleService
    ): ArticlesRemoteDataSource = ArticlesRetrofitDataSource(articleService)

    @Provides
    fun provideArticlesLocalDatasource(
        articleDao: ArticleDao
    ): ArticlesLocalDatasource = ArticlesRoomDatasource(articleDao)

    @Provides
    fun provideArticleRepository(
        localDatasource: ArticlesLocalDatasource,
        remoteDataSource: ArticlesRemoteDataSource
    ): ArticleDatasourceRepository = ArticleDatasource(localDatasource, remoteDataSource)
}
