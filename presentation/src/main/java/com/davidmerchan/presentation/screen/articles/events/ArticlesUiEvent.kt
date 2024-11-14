package com.davidmerchan.presentation.screen.articles.events

sealed interface ArticlesUiEvent {
    data object LoadArticles : ArticlesUiEvent
    data class DeleteArticle(val id: Long): ArticlesUiEvent
    data class UndoDeleteArticle(val id: Long): ArticlesUiEvent
}