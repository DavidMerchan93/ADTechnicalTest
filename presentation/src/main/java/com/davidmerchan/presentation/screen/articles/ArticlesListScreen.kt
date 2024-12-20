package com.davidmerchan.presentation.screen.articles

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
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
import com.davidmerchan.core.ui.theme.ADTechnicalTestTheme
import com.davidmerchan.core.utils.Toast
import com.davidmerchan.core.utils.checkUrl
import com.davidmerchan.core.utils.toast
import com.davidmerchan.domain.entitie.Article
import com.davidmerchan.domain.entitie.ArticleId
import com.davidmerchan.presentation.R
import com.davidmerchan.presentation.screen.articles.events.ArticlesUiEvent
import com.davidmerchan.presentation.screen.articles.states.ArticlesUiState
import com.davidmerchan.presentation.viewModel.ArticlesViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesListScreen(
    modifier: Modifier = Modifier,
    articlesViewModel: ArticlesViewModel = hiltViewModel(),
    onGoToDetail: (id: ArticleId, articleUrl: String) -> Unit
) {
    val context = LocalContext.current
    val uiState by articlesViewModel.articlesState.collectAsStateWithLifecycle()
    val connectionState by rememberConnectivityState()

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutine = rememberCoroutineScope()

    val isConnected by remember(connectionState) {
        derivedStateOf { connectionState === NetworkConnectionState.Available }
    }

    var showMessageUrl by remember { mutableStateOf(false) }

    if (showMessageUrl) {
        context.toast(stringResource(R.string.article_no_url_error))
        showMessageUrl = false
    }

    DeleteArticleSnackbar(
        uiState = uiState,
        snackBarHostState = snackBarHostState,
        coroutineScope = coroutine,
        title = stringResource(R.string.article_deleted_message),
        actionTitle = stringResource(R.string.article_deleted_undo_button),
        onUndoDeleteArticle = { articleId ->
            articlesViewModel.handleArticleEvent(
                ArticlesUiEvent.UndoDeleteArticle(articleId)
            )
        }
    )

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
                            articles = uiState.articles.toImmutableList(),
                            onDeleteArticle = { id ->
                                articlesViewModel.handleArticleEvent(
                                    ArticlesUiEvent.DeleteArticle(id)
                                )
                            },
                            onGoToDetail = { id, url ->
                                if (url.checkUrl()) {
                                    onGoToDetail(id, url)
                                } else {
                                    showMessageUrl = true
                                }
                            }
                        )
                    }

                    uiState.errorDeletedArticle != null -> {
                        Toast(context, stringResource(R.string.article_deleted_error_message))
                    }

                    uiState.error != null -> ErrorScreen()

                    else -> ErrorScreen(error = stringResource(R.string.empty_articles_error))
                }
            }
        }
    }
}

@Suppress("LongParameterList")
@Composable
fun DeleteArticleSnackbar(
    uiState: ArticlesUiState,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    title: String,
    actionTitle: String,
    onUndoDeleteArticle: (id: ArticleId) -> Unit
) {
    val currentOnUndoDeleteArticle by rememberUpdatedState(onUndoDeleteArticle)

    LaunchedEffect(uiState.successDeletedArticle) {
        uiState.successDeletedArticle?.let { articleId ->
            coroutineScope.launch {
                val action = snackBarHostState.showSnackbar(
                    message = title,
                    duration = SnackbarDuration.Short,
                    actionLabel = actionTitle,
                    withDismissAction = false
                )

                when (action) {
                    SnackbarResult.Dismissed -> Unit
                    SnackbarResult.ActionPerformed -> {
                        currentOnUndoDeleteArticle(articleId)
                        snackBarHostState.currentSnackbarData?.dismiss()
                    }
                }
            }
        }
    }
}

@Composable
fun ArticlesContent(
    articles: ImmutableList<Article>,
    onDeleteArticle: (id: ArticleId) -> Unit,
    modifier: Modifier = Modifier,
    onGoToDetail: (id: ArticleId, url: String) -> Unit
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
                    onGoToDetail = {
                        onGoToDetail(article.id, article.storyUrl)
                    },
                )
            }
        }
    }
}

@Composable
fun ArticleItem(
    article: Article,
    modifier: Modifier = Modifier,
    onGoToDetail: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onGoToDetail() }
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = article.title,
            softWrap = true,
            maxLines = 2,
            color = MaterialTheme.colorScheme.onBackground,
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
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 16.sp
            )
            Text(
                text = article.createdDate,
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
    }
}

@Preview
@Composable
internal fun ArticlesItemPreview() {
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
            onGoToDetail = {}
        )
    }
}

@Preview
@Composable
internal fun ArticlesContentPreview() {
    ADTechnicalTestTheme {
        ArticlesContent(
            articles = listOf(
                Article(
                    1,
                    "Title 1",
                    "Content 1",
                    "Author 1",
                    "2024-10-12",
                    "http://google.com"
                ),
                Article(
                    2,
                    "Title 2",
                    "Content 2",
                    "Author 2",
                    "2024-10-13",
                    "http://google.com"
                )
            ).toImmutableList(),
            onDeleteArticle = {},
            onGoToDetail = { id, url ->
                println("Go to detail: $id - $url")
            }
        )
    }
}
