package com.davidmerchan.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NetworkChangeReceiver(
    private val onNetworkChange: (Boolean) -> Unit
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val networkValidator = NetworkValidator(context)
            onNetworkChange(networkValidator.isConnected())
        }
    }
}
