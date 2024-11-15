package com.davidmerchan.core.utils

import android.util.Patterns

fun String.checkUrl(): Boolean {
    return Patterns.WEB_URL.matcher(this).matches()
}
