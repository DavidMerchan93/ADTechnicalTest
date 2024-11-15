package com.davidmerchan.data.datasource

import com.davidmerchan.data.mapper.mapToDomain
import com.davidmerchan.network.api.ArticleService
import com.davidmerchan.network.model.ArticlesResponse
import com.davidmerchan.network.model.Hits
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ArticlesRetrofitDataSourceTest {

    private val articleService: ArticleService = mockk()
    private lateinit var articlesRemoteDataSource: ArticlesRemoteDataSource
    
    @Before
    fun setUp() {
        articlesRemoteDataSource = ArticlesRetrofitDataSource(articleService)
    }

    @After
    fun tearDown() {
        confirmVerified(articleService)
    }

    @Test
    fun `getArticles handles pagination correctly with large number of articles`() = runBlocking {
        // Given
        val hitsMock: Hits = mockk {
            every { storyId } returns 1
            every { storyTitle } returns "Title"
            every { storyUrl } returns "Url"
            every { author } returns "Author"
            every { commentText } returns "Comment"
            every { createdAt } returns "Created At"
            every { tags } returns listOf("Tag1", "Tag2")
            every { createdAtI } returns 1000000000
            every { parentId } returns 2
            every { updatedAt } returns "Updated At"
        }
        val articleResponse: ArticlesResponse = mockk {
            every { hits } returns listOf(hitsMock)
        }

        coEvery { articleService.getArticles() } returns articleResponse

        // When
        val actualArticles = articlesRemoteDataSource.getArticles()

        // Then
        coVerify {
            articleService.getArticles()
        }
        assertEquals(articleResponse.mapToDomain(), actualArticles)
    }
    
    @Test
    fun `getArticles throws exception when service encounters an error`() = runBlocking {
        // Given
        val errorMessage = "Service error"
        coEvery { articleService.getArticles() } throws Exception(errorMessage)

        // When
        var actualException: Exception? = null
        try {
            articlesRemoteDataSource.getArticles()
        } catch (e: Exception) {
            actualException = e
        }

        // Then
        coVerify {
            articleService.getArticles()
        }
        assertEquals(errorMessage, actualException?.message)
    }
}
