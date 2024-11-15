package com.davidmerchan.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidmerchan.core.R
import com.davidmerchan.core.ui.theme.ADTechnicalTestTheme

@Composable
fun ConnectionMessage(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.error)
            .padding(8.dp),
        text = stringResource(R.string.no_connection_error),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onError,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
}

@Preview
@Composable
internal fun ConnectionMessagePreview() {
    ADTechnicalTestTheme {
        ConnectionMessage()
    }
}
