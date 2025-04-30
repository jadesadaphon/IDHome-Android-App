package com.app.jade.dev.idhome.prasat.data.datasource.api.idhome

import android.app.AlertDialog
import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.text.HtmlCompat
import com.app.jade.dev.idhome.prasat.AppConfig
import com.app.jade.dev.idhome.prasat.BuildConfig
import com.app.jade.dev.idhome.prasat.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class TransferApi {

    companion object {
        private const val TAG = "TransferApi"
    }

    suspend fun transferApi(context: Context): JSONObject? {
        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val host = AppConfig.API_PI
                val url = "${host}transfer"
                val request = Request.Builder()
                    .url(url)
                    .build()
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        return@withContext JSONObject(responseBody!!)
                    } else {
                        Log.e(TAG, "HTTP response status codes : ${response.code}")
//                        withContext(Dispatchers.Main) {
//                            Handler(Looper.getMainLooper()).postDelayed({
//
//                                val sound = MediaPlayer.create(context, R.raw.error_when_entering_the_game_menu)
//                                val strMessage = HtmlCompat.fromHtml(String.format(context.getString(
//                                    R.string.check_the_barcode_and_try_again),code), HtmlCompat.FROM_HTML_MODE_COMPACT)
//                                val strTitle = context.getString(R.string.product_not_found)
//                                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
//                                builder
//                                    .setMessage(strMessage)
//                                    .setTitle(strTitle)
//                                val dialog: AlertDialog = builder.create()
//
//                                sound.start()
//                                dialog.show()
//
//                            }, 500)
//                        }
                        throw IOException("Unexpected HTTP response status codes : ${response.code}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//                }
                return@withContext null
            }
        }
    }
}