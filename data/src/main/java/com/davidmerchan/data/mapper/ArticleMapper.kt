package com.davidmerchan.data.mapper

import com.davidmerchan.database.entity.ArticleEntity
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.network.model.ArticlesResponse

fun ArticlesResponse.mapToDomain(): List<Article> {
    return hits.map {
        Article(
            id = it.storyId.toLong(),
            title = it.storyTitle ?: "N/A",
            content = it.commentText ?: "N/A",
            author = it.author,
            createdDate = it.createdAt,
            storyUrl = it.storyUrl ?: "N/A"
        )
    }
}

fun Article.mapToEntity(): ArticleEntity {
    return ArticleEntity(
        id = id,
        title = title,
        content = content,
        author = author,
        createdDate = createdDate,
        storyUrl = storyUrl,
    )
}

fun ArticleEntity.mapToDomain(): Article {
    return Article(
        id = id,
        title = title,
        content = content,
        author = author,
        createdDate = createdDate,
        storyUrl = storyUrl,
    )
}
