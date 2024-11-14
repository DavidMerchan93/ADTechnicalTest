package com.davidmerchan.presentation.uiState

import com.davidmerchan.domain.entitie.Article

data class ArticlesScreenState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String? = null
)
