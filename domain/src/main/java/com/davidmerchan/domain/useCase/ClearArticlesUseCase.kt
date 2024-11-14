package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleLocalManagerRepository
import javax.inject.Inject

class ClearArticlesUseCase @Inject constructor(
    private val articleManager: ArticleLocalManagerRepository
) {
    operator fun invoke() {
        return when (val result = articleManager.clearArticles()) {
            is Resource.Success -> result.data
            is Resource.Error -> throw result.exception
        }
    }
}
