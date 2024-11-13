package com.davidmerchan.data.model

import com.davidmerchan.domain.entitie.Article
import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("hits")
    val hits: List<Hits>
) {
    fun mapToDomain(): List<Article> {
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
}
