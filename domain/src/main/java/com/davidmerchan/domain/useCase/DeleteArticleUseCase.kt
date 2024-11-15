package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.entitie.ArticleId
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleLocalManagerRepository
import javax.inject.Inject

class DeleteArticleUseCase @Inject constructor(
    private val articleManager: ArticleLocalManagerRepository
) {
    suspend operator fun invoke(id: ArticleId): Resource<Long> = when (val result = articleManager.deleteArticle(id)) {
        is Resource.Success -> Resource.Success(id)
        is Resource.Error -> result
    }
}
