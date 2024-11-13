package com.davidmerchan.data.model

internal data class Hits(
    val storyId: Int,
    val storyTitle: String,
    val storyUrl: String,
    val author: String,
    val commentText: String,
    val createdAt: String,
    val tags: List<String>,
    val createdAtI: Int,
    val objectID: String,
    val parentId: Int,
    val updatedAt: String
)
