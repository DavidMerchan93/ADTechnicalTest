package com.davidmerchan.core.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ShareIcon(context: Context, url: String) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, url)
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    Icon(
        imageVector = Icons.Default.Share,
        contentDescription = "Share",
        modifier = Modifier.clickable(onClick = {
            context.startActivity(shareIntent)
        })
    )
}
