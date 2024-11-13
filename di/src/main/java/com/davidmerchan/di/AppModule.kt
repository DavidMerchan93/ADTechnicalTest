package com.davidmerchan.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.davidmerchan.data.local.AppDatabase
import com.davidmerchan.data.local.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(
        @ApplicationContext applicationContext: Context
    ): RoomDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "ADTechnicalDatabase"
    ).build()

    @Provides
    @Singleton
    fun provideArticleDao(
        database: AppDatabase
    ): ArticleDao = database.articleDao()
}