package com.davidmerchan.core.network

sealed interface NetworkConnectionState {
    data object Available : NetworkConnectionState
    data object Unavailable : NetworkConnectionState
    data object Lost : NetworkConnectionState
}
