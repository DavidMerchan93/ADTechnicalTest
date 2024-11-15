package com.davidmerchan.core.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun Toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
