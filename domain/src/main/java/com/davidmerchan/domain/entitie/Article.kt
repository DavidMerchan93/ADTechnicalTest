package com.davidmerchan.domain.entitie

data class Article(
    val id: Long,
    val title: String,
    val content: String,
    val author: String,
    val createdDate: String,
    val storyUrl: String,
)