package com.davidmerchan.presentation.screen.articles.events

import com.davidmerchan.domain.entitie.ArticleId

sealed interface ArticlesUiEvent {
    data object LoadArticles : ArticlesUiEvent
    data object RestoreAllArticles : ArticlesUiEvent
    data class DeleteArticle(val id: ArticleId): ArticlesUiEvent
    data class UndoDeleteArticle(val id: ArticleId): ArticlesUiEvent
}