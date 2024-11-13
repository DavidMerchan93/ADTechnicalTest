package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.repository.ArticleLocalManagerRepository
import javax.inject.Inject

class DeleteArticleUseCase @Inject constructor(
    private val articleManager: ArticleLocalManagerRepository
) {
    operator fun invoke(id: Long) {
        articleManager.deleteArticle(id)
    }
}
