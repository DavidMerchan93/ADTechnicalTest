package com.davidmerchan.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.useCase.DeleteArticleUseCase
import com.davidmerchan.domain.useCase.GetArticlesUseCase
import com.davidmerchan.presentation.uiState.ArticlesScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val deleteArticlesUseCase: DeleteArticleUseCase
) : ViewModel() {

    private val _articlesState = MutableStateFlow(ArticlesScreenState(isLoading = true))
    val articlesState: StateFlow<ArticlesScreenState>
        get() = _articlesState.asStateFlow()

    init {
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            _articlesState.value = when (val result = getArticlesUseCase()) {
                is Resource.Success -> ArticlesScreenState(articles = result.data)
                is Resource.Error -> ArticlesScreenState(error = result.exception.message)
            }
        }
    }

    private fun deleteArticle(id: Long) {
        viewModelScope.launch {
            deleteArticlesUseCase(id)
        }
    }
}
