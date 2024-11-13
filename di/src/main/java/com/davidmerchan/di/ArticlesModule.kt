package com.davidmerchan.di

import com.davidmerchan.data.repository.ArticlesLocalDatasource
import com.davidmerchan.domain.repository.ArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ArticlesModule {

    @Provides
    fun provideArticleRepository(): ArticleRepository = ArticlesLocalDatasource()

}
