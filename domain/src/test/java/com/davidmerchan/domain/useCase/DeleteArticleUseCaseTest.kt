package com.davidmerchan.domain.useCase

import com.davidmerchan.domain.entitie.Resource
import com.davidmerchan.domain.repository.ArticleLocalManagerRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DeleteArticleUseCaseTest {

    private val articleManager: ArticleLocalManagerRepository = mockk()

    private lateinit var deleteArticleUseCase: DeleteArticleUseCase

    @Before
    fun setUp() {
        deleteArticleUseCase = DeleteArticleUseCase(articleManager)
    }

    @Before
    fun tearDown() {
        confirmVerified(articleManager)
    }

    @Test
    fun `should return a success resource when the article is successfully deleted`() {
        // Given
        val id = 100L
        every { articleManager.deleteArticle(id) } returns Resource.Success(Unit)

        // When
        val result = deleteArticleUseCase(id)

        // Then
        verify {
            articleManager.deleteArticle(id)
        }
        assertEquals(result, Resource.Success(100L))
    }

    @Test
    fun `should handle a database error when deleting the article`() {
        // Given
        val id = 100L
        val exception = Exception("Database error")
        every { articleManager.deleteArticle(id) } returns Resource.Error(exception)

        // When
        val result = deleteArticleUseCase(id)

        // Then
        verify {
            articleManager.deleteArticle(id)
        }
        assertEquals(result, Resource.Error(exception))
    }

    @Test
    fun `should handle a concurrent modification error when deleting the article`() {
        // Given
        val id = 100L
        val exception = Exception("Concurrent modification error")
        every { articleManager.deleteArticle(id) } returns Resource.Error(exception)

        // When
        val result = deleteArticleUseCase(id)

        // Then
        verify {
            articleManager.deleteArticle(id)
        }
        assertEquals(result, Resource.Error(exception))
    }

}
