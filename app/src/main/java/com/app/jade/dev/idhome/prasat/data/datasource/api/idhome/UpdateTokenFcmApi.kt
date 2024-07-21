package com.app.jade.dev.idhome.prasat.data.datasource.api.idhome

import android.util.Log
import com.app.jade.dev.idhome.prasat.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class UpdateTokenFcmApi {

    companion object {
        private const val TAG = "UpdateTokenFCM"
    }

    suspend fun updateTokenFCM(userCode: String, token: String): JSONObject {
        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val host = BuildConfig.HOST_PYTHON_API
                val url = "${host}newTokenFCM"
                val formBody = FormBody.Builder()
                    .add("token", token)
                    .add("userCode", userCode)
                    .build()

                val request = Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build()

                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        JSONObject(responseBody!!)
                    } else {
                        Log.e(TAG, "HTTP response status codes : ${response.code}")
                        throw IOException("Unexpected HTTP response status codes : ${response.code}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
                throw IOException("Error : ${e.message}")
            }
        }

    }
}