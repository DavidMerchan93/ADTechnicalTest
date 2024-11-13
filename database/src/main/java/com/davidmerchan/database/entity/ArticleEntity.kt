package com.davidmerchan.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class ArticleEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val content: String,
    val author: String,
    @ColumnInfo("is_deleted") val isDeleted: Boolean = false,
    @ColumnInfo("created_date") val createdDate: String,
    @ColumnInfo("story_url") val storyUrl: String,
)
