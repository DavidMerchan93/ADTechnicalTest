package com.davidmerchan.network.model

import com.google.gson.annotations.SerializedName

data class Hits(
    @SerializedName("story_id")
    val storyId: Int,
    @SerializedName("story_title")
    val storyTitle: String?,
    @SerializedName("story_url")
    val storyUrl: String?,
    @SerializedName("author")
    val author: String,
    @SerializedName("comment_text")
    val commentText: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("created_at_i")
    val createdAtI: Long,
    @SerializedName("parent_id")
    val parentId: Long,
    @SerializedName("updated_at")
    val updatedAt: String
)
