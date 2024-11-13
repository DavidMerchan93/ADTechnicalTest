package com.davidmerchan.presentation

import androidx.lifecycle.ViewModel
import com.davidmerchan.domain.useCase.GetArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
): ViewModel() {

    init {
        getArticles()
    }

    fun getArticles() {
        GlobalScope.launch {
            getArticlesUseCase()
        }
    }
}
