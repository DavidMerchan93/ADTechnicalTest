package com.davidmerchan.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davidmerchan.database.dao.ArticleDao
import com.davidmerchan.database.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
