package com.example.test.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class Connection(private val context: Context) {

    @Suppress("DEPRECATION")
    fun isInternetAvailable(): Boolean {
        val connectionManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = connectionManager.getNetworkCapabilities(connectionManager.activeNetwork)

            if (capabilities != null) {
                return when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }

        } else {
            val activeNetworkInfo = connectionManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected)
                return true
        }

        return false
    }
}