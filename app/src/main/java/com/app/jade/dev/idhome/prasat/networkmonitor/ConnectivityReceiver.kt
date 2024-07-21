package com.app.jade.dev.idhome.prasat.networkmonitor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.app.jade.dev.idhome.prasat.data.model.EkycViewModel

class ConnectivityReceiver(private val viewModel: EkycViewModel) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            val isConnected = isOnline(context)
            if (isConnected) {
                val networkType = getConnectionType(context)
                viewModel.networkType.value = networkType["type"] as Int
            } else {
                viewModel.networkType.value = 0
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    private fun isOnline(context: Context): Boolean {
        return try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            networkCapabilities != null && (
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    )
        } catch (e: NullPointerException) {
            e.printStackTrace()
            false
        }
    }

    private fun getConnectionType(context: Context): Map<String, Any> {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return if (networkCapabilities != null) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                mapOf("type" to 1, "message" to "Connected via Wi-Fi")
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                mapOf("type" to 2, "message" to "Connected via Cellular")
            } else {
                mapOf("type" to 0, "message" to "Connected, but unknown connection type")
            }
        } else {
            mapOf("type" to 0, "message" to "Connected, but unknown connection type")
        }
    }


}
