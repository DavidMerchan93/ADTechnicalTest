package com.davidmerchan.data.model

import com.davidmerchan.domain.entitie.Article

internal data class ArticlesResponse(
    val hits: List<Hits>
) {
    fun mapToDomain(): List<Article> {
        return hits.map {
            Article(
                id = it.storyId.toLong(),
                title = it.storyTitle,
                content = it.commentText,
                author = it.author,
                createdDate = it.createdAt,
                storyUrl = it.storyUrl
            )
        }
    }
}
