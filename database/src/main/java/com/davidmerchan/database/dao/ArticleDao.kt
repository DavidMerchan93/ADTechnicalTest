package com.davidmerchan.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.davidmerchan.database.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article")
    fun getArticles(): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertArticles(vararg articles: ArticleEntity)

    @Query("UPDATE article SET is_deleted = 1 WHERE id = :id")
    fun deleteArticle(id: Long)

    @Query("DELETE FROM article")
    fun clearArticles()
}
