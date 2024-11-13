package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleLocalManagerRepository
import javax.inject.Inject

class DeleteArticleUseCase @Inject constructor(
    private val articleManager: ArticleLocalManagerRepository
) {
    operator fun invoke(id: Long) = when (val result = articleManager.deleteArticle(id)) {
        is Resource.Success -> result.data
        is Resource.Error -> throw result.exception
    }
}
