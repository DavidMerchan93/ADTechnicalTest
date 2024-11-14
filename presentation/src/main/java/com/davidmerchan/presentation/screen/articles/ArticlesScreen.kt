package com.davidmerchan.presentation.screen.articles

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davidmerchan.core.network.NetworkConnectionState
import com.davidmerchan.core.network.rememberConnectivityState
import com.davidmerchan.core.ui.ConnectionMessage
import com.davidmerchan.core.ui.ErrorScreen
import com.davidmerchan.core.ui.LoadingScreen
import com.davidmerchan.core.ui.SwipeToDeleteBox
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.ArticleId
import com.davidmerchan.presentation.R
import com.davidmerchan.presentation.screen.articles.events.ArticlesUiEvent
import com.davidmerchan.presentation.theme.ADTechnicalTestTheme
import com.davidmerchan.presentation.viewModel.ArticlesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(
    modifier: Modifier = Modifier,
    articlesViewModel: ArticlesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by articlesViewModel.articlesState.collectAsStateWithLifecycle()
    val connectionState by rememberConnectivityState()

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutine = rememberCoroutineScope()

    val isConnected by remember(connectionState) {
        derivedStateOf { connectionState === NetworkConnectionState.Available }
    }

    LaunchedEffect(uiState.successDeletedArticle) {
        uiState.successDeletedArticle?.let { articleId ->
            coroutine.launch {
                val action = snackBarHostState.showSnackbar(
                    message = "Articulo eliminado",
                    duration = SnackbarDuration.Short,
                    actionLabel = "Deshacer",
                    withDismissAction = false
                )

                when (action) {
                    SnackbarResult.Dismissed -> Unit
                    SnackbarResult.ActionPerformed -> {
                        articlesViewModel.handleArticleEvent(
                            ArticlesUiEvent.UndoDeleteArticle(articleId)
                        )
                        snackBarHostState.currentSnackbarData?.dismiss()
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.articles_title_screen))
                },
                actions = {
                    Icon(
                        modifier = Modifier.clickable {
                            articlesViewModel.handleArticleEvent(ArticlesUiEvent.RestoreAllArticles)
                        },
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Restore articles"
                    )
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->

        PullToRefreshBox(
            modifier = Modifier.padding(innerPadding),
            isRefreshing = uiState.isRefreshing,
            onRefresh = {
                articlesViewModel.handleArticleEvent(ArticlesUiEvent.LoadArticles)
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isConnected.not()) {
                    ConnectionMessage()
                }

                when {
                    uiState.isLoading ->
                        LoadingScreen(message = stringResource(R.string.title_loading_articles))

                    uiState.articles.isNotEmpty() -> {
                        ArticlesContent(
                            articles = uiState.articles,
                            onDeleteArticle = { id ->
                                articlesViewModel.handleArticleEvent(
                                    ArticlesUiEvent.DeleteArticle(id)
                                )
                            }
                        )
                    }

                    uiState.errorDeletedArticle != null -> {
                        Toast.makeText(
                            context,
                            "No fue posible eliminar el articulo",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    uiState.error != null -> ErrorScreen()

                    else -> ErrorScreen(error = stringResource(R.string.empty_articles_error))
                }
            }
        }
    }
}

@Composable
fun ArticlesContent(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onDeleteArticle: (id: ArticleId) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = articles,
            key = { it.id }
        ) { article ->
            SwipeToDeleteBox(
                modifier = Modifier.animateItem(),
                onDelete = {
                    onDeleteArticle(article.id)
                }
            ) {
                ArticleItem(
                    article = article,
                    onArticleClick = {},
                )
            }
        }
    }
}

@Composable
fun ArticleItem(
    modifier: Modifier = Modifier,
    article: Article,
    onArticleClick: (Article) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = article.title,
            softWrap = true,
            maxLines = 2,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = article.author,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 16.sp
            )
            Text(
                text = article.createdDate,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
    }
}

@Preview
@Composable
internal fun ArticlesScreenPreview() {
    ADTechnicalTestTheme {
        ArticleItem(
            article = Article(
                1,
                "Title",
                "Content",
                "Author",
                "2024-10-12",
                "http://google.com"
            ),
            onArticleClick = {}
        )
    }
}
