package com.davidmerchan.domain.entitie

typealias ArticleId = Long

data class Article(
    val id: ArticleId,
    val title: String,
    val content: String,
    val author: String,
    val createdDate: String,
    val storyUrl: String,
    val isDeleted: Boolean = false
)
