package com.davidmerchan.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.davidmerchan.database.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article")
    fun getArticles(): List<ArticleEntity>

    @Query("SELECT * FROM article WHERE id = :id")
    fun getArticleById(id: Long): ArticleEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertArticles(vararg articles: ArticleEntity)

    @Query("UPDATE article SET is_deleted = 1 WHERE id = :id")
    fun deleteArticle(id: Long)

    @Query("UPDATE article SET is_deleted = 0 WHERE id = :id")
    fun restoreArticle(id: Long)

    @Query("UPDATE article SET is_deleted = 0")
    fun restoreAllArticle()

    @Transaction
    fun restore(id: Long): ArticleEntity {
        restoreArticle(id)
        return getArticleById(id)
    }

    @Transaction
    fun restoreAll(): List<ArticleEntity> {
        restoreAllArticle()
        return getArticles()
    }
}
