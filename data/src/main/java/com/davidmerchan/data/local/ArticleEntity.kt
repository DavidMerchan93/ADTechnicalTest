package com.davidmerchan.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.davidmerchan.domain.entitie.Article

@Entity(tableName = "article")
data class ArticleEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val content: String,
    val author: String,
    @ColumnInfo("created_date") val createdDate: String,
    @ColumnInfo("story_url") val storyUrl: String,
) {
    fun mapToDomain(): Article {
        return Article(
            id = id,
            title = title,
            content = content,
            author = author,
            createdDate = createdDate,
            storyUrl = storyUrl,
        )
    }
}
