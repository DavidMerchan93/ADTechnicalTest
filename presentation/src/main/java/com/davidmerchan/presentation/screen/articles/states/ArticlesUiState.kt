package com.davidmerchan.presentation.screen.articles.states

import com.davidmerchan.domain.entitie.Article

data class ArticlesUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String? = null,
    val successDeletedArticle: Long? = null,
    val errorDeletedArticle: Long? = null,
)
