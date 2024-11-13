package com.davidmerchan.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article")
    fun getArticles(): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(vararg articles: ArticleEntity)
}
