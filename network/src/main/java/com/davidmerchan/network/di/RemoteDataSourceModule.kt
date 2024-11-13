package com.davidmerchan.network.di

import com.davidmerchan.network.ArticleService
import com.davidmerchan.network.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideApiManager(
        retrofitBuilder: RetrofitBuilder
    ): Retrofit = retrofitBuilder.create()

    @Provides
    @Singleton
    fun provideArticlesApi(
        retrofit: Retrofit
    ): ArticleService = retrofit.create(ArticleService::class.java)

}