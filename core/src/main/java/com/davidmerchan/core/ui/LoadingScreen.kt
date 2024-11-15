package com.davidmerchan.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.davidmerchan.core.R
import com.davidmerchan.core.ui.theme.ADTechnicalTestTheme

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    message: String? = null
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(message ?: stringResource(R.string.general_loading))
        CircularProgressIndicator()
    }
}

@Preview
@Composable
internal fun LoadingScreenPreview() {
    ADTechnicalTestTheme {
        LoadingScreen()
    }
}
