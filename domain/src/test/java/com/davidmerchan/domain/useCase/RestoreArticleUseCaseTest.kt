package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleLocalManagerRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RestoreArticleUseCaseTest {

    private lateinit var restoreArticleUseCase: RestoreArticleUseCase
    private val articleManager: ArticleLocalManagerRepository = mockk()

    @Before
    fun setUp() {
        restoreArticleUseCase = RestoreArticleUseCase(articleManager)
    }

    @After
    fun tearDown() {
        confirmVerified(articleManager)
    }

    @Test
    fun `should return a success resource when the article is successfully restored`() {
        // Given
        val id = 100L
        val article: Article = mockk()

        every { articleManager.restoreArticle(id) } returns
                Resource.Success(article)

        // When
        val result = restoreArticleUseCase(id)

        // Then
        verify {
            articleManager.restoreArticle(id)
        }
        confirmVerified(article)
        assertEquals(result, Resource.Success(article))
    }

    @Test
    fun `should return an error resource when the article manager returns an error`() {
        // Given
        val id = 100L
        val exception = Exception("Error restoring article")

        every {
            articleManager.restoreArticle(id)
        } returns Resource.Error(exception)

        // When
        val result = restoreArticleUseCase(id)

        // Then
        verify {
            articleManager.restoreArticle(id)
        }
        assertEquals(result, Resource.Error(exception))
    }
}