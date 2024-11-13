package com.davidmerchan.di

import android.content.Context
import androidx.room.Room
import com.davidmerchan.database.AppDatabase
import com.davidmerchan.database.dao.ArticleDao
import com.davidmerchan.network.manager.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(
        @ApplicationContext applicationContext: Context
    ): AppDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "ADTechnicalDatabase"
    ).build()

    @Provides
    @Singleton
    fun provideArticleDao(
        database: AppDatabase
    ): ArticleDao = database.articleDao()

    @Provides
    @Singleton
    fun provideApiManager(): Retrofit = RetrofitBuilder.create()
}
