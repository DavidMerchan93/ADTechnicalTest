package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleDatasourceRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticleDatasourceRepository,
    private val saveArticlesUseCase: SaveArticlesUseCase
) {
    suspend operator fun invoke(): Resource<List<Article>> {
        return when (val result = articlesRepository.getArticles()) {
            is Resource.Success -> {
                saveArticlesUseCase(result.data)
                result
            }
            is Resource.Error -> result
        }
    }
}
