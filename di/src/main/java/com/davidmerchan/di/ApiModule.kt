package com.davidmerchan.di

import com.davidmerchan.data.ArticleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideArticlesApi(
        retrofit: Retrofit
    ): ArticleService = retrofit.create(ArticleService::class.java)

}
