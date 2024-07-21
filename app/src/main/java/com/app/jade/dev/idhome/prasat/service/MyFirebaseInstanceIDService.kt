package com.app.jade.dev.idhome.prasat.service

import android.Manifest
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import com.app.jade.dev.idhome.prasat.BuildConfig
import com.app.jade.dev.idhome.prasat.R
import com.app.jade.dev.idhome.prasat.data.datasource.api.idhome.LoginApi
import com.app.jade.dev.idhome.prasat.ui.EkycActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MyFirebaseInstanceIDService: FirebaseMessagingService() {


    override fun onMessageReceived(message: RemoteMessage) {
        showNotification(message)
    }

    override fun onNewToken(token: String) {
        val sharedPreferences = getSharedPreferences("FCM_PREF", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("fcm_token", token).apply()
    }


    private fun showNotification(message: RemoteMessage) {
        Log.d(TAG, "From: ${message.from}")
        message.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: " + message.data)
        }
        //ตรวจสอบว่าข้อความมีเพย์โหลดการแจ้งเตือนหรือไม่
        message.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }

        createNotificationChannel()
        // unique id to show new notification each time we click notification, if you want to replace previous use a constant as id
        val date = Date()
        val notificationId = SimpleDateFormat("ddHHmmss", Locale.US).format(date).toInt()

        // handle notification click, start EkycActivity by Tapping notification
        val mainIntent = Intent(this, EkycActivity::class.java)

        // if you want to pass data in notification and get in required activity
        mainIntent.putExtra("KEY_NAME", "Admin Jade")
        mainIntent.putExtra("KEY_EMAIL", "it@idhomr2015.com")
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val mainPendingIntent = PendingIntent.getActivity(this, 1, mainIntent, PendingIntent.FLAG_IMMUTABLE)

        // creating notification builder
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)  // notification icon
            .setContentTitle("Notification Title")  // notification title
            .setContentText("This is the description of the notification, can be of multiple lines")  // notification description
            .setPriority(NotificationCompat.PRIORITY_HIGH)  // notification priority
            .setAutoCancel(true)  // cancel notification on click
            .setContentIntent(mainPendingIntent)  // add click intent

        // notification manager
        val notificationManagerCompat = NotificationManagerCompat.from(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManagerCompat.notify(notificationId, notificationBuilder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "MyNotification"
            val description = "My notification channel description"
            // importance of your notification
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationChannel.description = description
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private companion object {
        private const val TAG = "MyFirebaseMsgService"
        private const val CHANNEL_ID = "channel01"
    }
}