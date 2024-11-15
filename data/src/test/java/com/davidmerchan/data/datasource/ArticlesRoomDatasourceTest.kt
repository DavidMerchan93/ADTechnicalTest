package com.davidmerchan.data.datasource

import com.davidmerchan.database.dao.ArticleDao
import com.davidmerchan.database.entity.ArticleEntity
import com.davidmerchan.domain.entitie.Article
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ArticlesRoomDatasourceTest {

    private val articleDao: ArticleDao = mockk()
    private lateinit var articlesLocalDatasource: ArticlesLocalDatasource

    @Before
    fun setUp() {
        articlesLocalDatasource = ArticlesRoomDatasource(articleDao)
    }

    @After
    fun tearDown() {
        confirmVerified(articleDao)
    }

    @Test
    fun `getArticles() should handle local data source and return articles correctly`() {

        // Given
        val article: ArticleEntity = mockk {
            every { id } returns 100L
            every { title } returns "Test Article"
            every { author } returns "Author"
            every { storyUrl } returns "http://example.com"
            every { content } returns "This is a test article"
            every { createdDate } returns "2022-01-01"
            every { isDeleted } returns false
        }

        every {
            articleDao.getArticles()
        } returns listOf(article)

        // When
        val result = articlesLocalDatasource.getArticles()

        // Then
        verify {
            articleDao.getArticles()

            article.id
            article.title
            article.author
            article.storyUrl
            article.content
            article.createdDate
            article.isDeleted
        }
        confirmVerified(article)
        assertEquals(
            result,
            listOf(
                Article(
                    id = 100L,
                    title = "Test Article",
                    author = "Author",
                    storyUrl = "http://example.com",
                    content = "This is a test article",
                    createdDate = "2022-01-01",
                    isDeleted = false
                )
            )
        )
    }

    @Test
    fun `saveArticles() should handle saving a large number of articles efficiently`() {
        // Given
        val article: Article = mockk {
            every { id } returns 10L
            every { title } returns "title"
            every { content } returns "content"
            every { author } returns "author"
            every { createdDate } returns "createdDate"
            every { storyUrl } returns "created"
            every { isDeleted } returns false
        }
        val articles = listOf(article)

        every {
            articleDao.insertArticles(any())
        } just runs

        // When
        articlesLocalDatasource.saveArticles(articles)

        // Then
        verify {
            articleDao.insertArticles(any())

            article.id
            article.title
            article.content
            article.author
            article.createdDate
            article.storyUrl
            article.isDeleted
        }
        confirmVerified(article)
    }
    
    @Test
    fun `deleteArticle() should handle deleting an article that exists in the database`() {
        // Given
        val articleId = 100L
    
        every {
            articleDao.deleteArticle(articleId)
        } just runs

        // When
        articlesLocalDatasource.deleteArticle(articleId)

        // Then
        verify {
            articleDao.deleteArticle(articleId)
        }
        confirmVerified(articleDao)
    }
    
    @Test
    fun `restoreArticle() should handle restoring an article that exists in the database and return the restored article`() {
        // Given
        val articleId = 100L
        val restoredArticle: ArticleEntity = mockk {
            every { id } returns articleId
            every { title } returns "Test Article"
            every { author } returns "Author"
            every { storyUrl } returns "http://example.com"
            every { content } returns "This is a test article"
            every { createdDate } returns "2022-01-01"
            every { isDeleted } returns true
        }

        every {
            articleDao.restore(articleId)
        } returns restoredArticle

        // When
        val result = articlesLocalDatasource.restoreArticle(articleId)

        // Then
        verify {
            articleDao.restore(articleId)

            restoredArticle.id
            restoredArticle.title
            restoredArticle.author
            restoredArticle.storyUrl
            restoredArticle.content
            restoredArticle.createdDate
            restoredArticle.isDeleted
        }
        confirmVerified(restoredArticle)
        assertEquals(
            result,
            Article(
                id = articleId,
                title = "Test Article",
                author = "Author",
                storyUrl = "http://example.com",
                content = "This is a test article",
                createdDate = "2022-01-01",
                isDeleted = true
            )
        )
    }

    @Test
    fun `restoreAllArticles() should handle restoring articles that exists in the database and return the restored articles`() {
        // Given
        val restoredArticle: ArticleEntity = mockk {
            every { id } returns 10L
            every { title } returns "Test Article"
            every { author } returns "Author"
            every { storyUrl } returns "http://example.com"
            every { content } returns "This is a test article"
            every { createdDate } returns "2022-01-01"
            every { isDeleted } returns true
        }

        every {
            articleDao.restoreAll()
        } returns listOf(restoredArticle)

        // When
        val result = articlesLocalDatasource.restoreAllArticles()

        // Then
        verify {
            articleDao.restoreAll()

            restoredArticle.id
            restoredArticle.title
            restoredArticle.author
            restoredArticle.storyUrl
            restoredArticle.content
            restoredArticle.createdDate
            restoredArticle.isDeleted
        }
        confirmVerified(restoredArticle)
        assertEquals(
            result,
            listOf(
                Article(
                    id = 10L,
                    title = "Test Article",
                    author = "Author",
                    storyUrl = "http://example.com",
                    content = "This is a test article",
                    createdDate = "2022-01-01",
                    isDeleted = true
                )
            )
        )
    }
}