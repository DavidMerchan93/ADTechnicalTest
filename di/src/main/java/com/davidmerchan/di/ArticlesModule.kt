package com.davidmerchan.di

import com.davidmerchan.data.local.ArticleDao
import com.davidmerchan.data.repository.ArticlesLocalDatasource
import com.davidmerchan.data.repository.ArticlesRemoteDataSource
import com.davidmerchan.domain.repository.ArticleDatasourceRepository
import com.davidmerchan.network.ArticleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ArticlesModule {

    @Provides
    fun provideRemoteArticleRepository(
        articleService: ArticleService
    ): ArticleDatasourceRepository = ArticlesRemoteDataSource(articleService)

    @Provides
    fun provideLocalArticleRepository(
        articleDao: ArticleDao
    ): ArticleDatasourceRepository = ArticlesLocalDatasource(articleDao)
}
