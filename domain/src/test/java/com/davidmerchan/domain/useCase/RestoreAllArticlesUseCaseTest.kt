package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleLocalManagerRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RestoreAllArticlesUseCaseTest {

    private val articleManager: ArticleLocalManagerRepository = mockk()
    private lateinit var restoreAllArticlesUseCase: RestoreAllArticlesUseCase

    @Before
    fun setUp() {
        restoreAllArticlesUseCase = RestoreAllArticlesUseCase(articleManager)
    }

    @After
    fun tearDown() {
        confirmVerified(articleManager)
    }

    @Test
    fun `should verify the response is correct for input values`() = runBlocking {
        // Given
        val articles = List(10) {
            Article(
                id = it.toLong(),
                title = "Title $it",
                author = "Author $it",
                createdDate = "2024-10-11",
                storyUrl = "http://example.com",
                content = "Article $it"
            )
        }
        coEvery { articleManager.restoreAllArticles() } returns Resource.Success(articles)

        // When
        val result = restoreAllArticlesUseCase()

        // Then
        coVerify {
            articleManager.restoreAllArticles()
        }
        assertEquals(result, Resource.Success(articles))
    }
    
    @Test
    fun `should handle Other errors when attempting to restore articles`() = runBlocking {

        val exception = Exception("Other error")

        // Given
        coEvery { articleManager.restoreAllArticles() } returns Resource.Error(exception)

        // When
        val result = restoreAllArticlesUseCase()

        // Then
        coVerify {
            articleManager.restoreAllArticles()
        }
        assertEquals(result, Resource.Error(exception))
    }
}
