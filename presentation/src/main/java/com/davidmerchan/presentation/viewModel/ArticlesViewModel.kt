package com.davidmerchan.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.useCase.DeleteArticleUseCase
import com.davidmerchan.domain.useCase.GetArticlesUseCase
import com.davidmerchan.presentation.screen.articles.events.ArticlesUiEvent
import com.davidmerchan.presentation.screen.articles.states.ArticlesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val deleteArticlesUseCase: DeleteArticleUseCase
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
            is ArticlesUiEvent.DeleteArticle -> deleteArticle(event.id)
            is ArticlesUiEvent.UndoDeleteArticle -> TODO()
        }
    }

    private fun onPullToRefreshArticles() {
        _articlesState.update {
            it.copy(isRefreshing = true)
        }
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            _articlesState.value = when (val result = getArticlesUseCase()) {
                is Resource.Success -> ArticlesUiState(articles = result.data)
                is Resource.Error -> ArticlesUiState(error = result.exception.message)
            }
        }
    }

    private fun deleteArticle(id: Long) {
        viewModelScope.launch {
            deleteArticlesUseCase(id)
        }
    }
}
