package com.davidmerchan.presentation.screen.detail

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.davidmerchan.core.ui.LoadingScreen
import com.davidmerchan.core.ui.ShareIcon
import com.davidmerchan.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    url: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.articles_detail_title_screen))
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            onBack()
                        },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                },
                actions = {
                    ShareIcon(context = context, url = url)
                }
            )
        }
    ) { innerPaddings ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings),
            factory = { cont ->
                WebView(cont).apply {
                    settings.javaScriptEnabled = true
                    settings.loadWithOverviewMode = true

                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            isLoading = true
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading = false
                        }
                    }
                }
            },
            update = {
                it.loadUrl(url)
            }
        )
        if (isLoading) {
            LoadingScreen()
        }
    }
}
