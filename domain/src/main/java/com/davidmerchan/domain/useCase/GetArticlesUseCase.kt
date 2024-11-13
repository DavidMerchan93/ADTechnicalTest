package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleDatasourceRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticleDatasourceRepository,
    private val saveArticlesUseCase: SaveArticlesUseCase
) {
    suspend operator fun invoke(): List<Article> {
        when (val result = articlesRepository.getArticles()) {
            is Resource.Success -> {
                saveArticlesUseCase(result.data)
                return result.data
            }
            is Resource.Error -> throw result.exception
        }
    }
}
