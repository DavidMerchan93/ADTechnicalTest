package com.davidmerchan.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmerchan.domain.entitie.ArticleId
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.useCase.DeleteArticleUseCase
import com.davidmerchan.domain.useCase.GetArticlesUseCase
import com.davidmerchan.domain.useCase.RestoreAllArticlesUseCase
import com.davidmerchan.domain.useCase.RestoreArticleUseCase
import com.davidmerchan.presentation.screen.articles.events.ArticlesUiEvent
import com.davidmerchan.presentation.screen.articles.states.ArticlesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val deleteArticlesUseCase: DeleteArticleUseCase,
    private val restoreArticleUseCase: RestoreArticleUseCase,
    private val restoreAllArticlesUseCase: RestoreAllArticlesUseCase,
) : ViewModel() {

    private val _articlesState = MutableStateFlow(ArticlesUiState(isLoading = true))
    val articlesState: StateFlow<ArticlesUiState>
        get() = _articlesState.asStateFlow()

    init {
        getArticles()
    }

    fun handleArticleEvent(event: ArticlesUiEvent) {
        when (event) {
            ArticlesUiEvent.LoadArticles -> onPullToRefreshArticles()
            ArticlesUiEvent.RestoreAllArticles -> restoreAllArticles()
            is ArticlesUiEvent.DeleteArticle -> deleteArticle(event.id)
            is ArticlesUiEvent.UndoDeleteArticle -> restoreArticle(event.id)
        }
    }

    private fun onPullToRefreshArticles() {
        _articlesState.update {
            it.copy(isRefreshing = true)
        }
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
            _articlesState.value = when (val result = getArticlesUseCase()) {
                is Resource.Success -> ArticlesUiState(articles = result.data)
                is Resource.Error -> ArticlesUiState(error = result.exception.message)
            }
        }
    }

    private fun deleteArticle(id: ArticleId) {
        viewModelScope.launch {
            val result = deleteArticlesUseCase(id)
            when (result) {
                is Resource.Success -> {
                    _articlesState.update {
                        ArticlesUiState(
                            articles = it.articles.filterNot { article -> article.id == id },
                            successDeletedArticle = id
                        )
                    }
                }

                is Resource.Error -> ArticlesUiState(errorDeletedArticle = id)
            }
        }
    }

    private fun restoreArticle(id: ArticleId) {
        viewModelScope.launch {
            when (val result = restoreArticleUseCase(id)) {
                is Resource.Success -> {
                    val articles = _articlesState.value.articles
                    val article = result.data

                    _articlesState.value = ArticlesUiState(
                        articles = articles.plus(article)
                            .sortedByDescending { it.createdDate },
                        successDeletedArticle = null,
                        errorDeletedArticle = null
                    )
                }

                is Resource.Error -> Unit
            }
        }
    }

    private fun restoreAllArticles() {
        viewModelScope.launch {
            when (val result = restoreAllArticlesUseCase()) {
                is Resource.Success -> {
                    _articlesState.value = ArticlesUiState(
                        articles = result.data.sortedByDescending { it.createdDate }
                    )
                }

                is Resource.Error -> Unit
            }
        }
    }
}
