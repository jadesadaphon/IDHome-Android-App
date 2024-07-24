package com.app.jade.dev.idhome.prasat.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.jade.dev.idhome.prasat.R
import com.app.jade.dev.idhome.prasat.ui.EkycActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
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
        Log.d(TAG, "Refreshed token: $token")
    }

    private fun showNotification(message: RemoteMessage) {
        var title = ""
        var body = ""
        var payload = mutableMapOf<String?, String?>()
        message.notification?.let {
            title = it.title!!
            body = it.body!!
        }
        Log.d(TAG, "From: ${message.from}")
        message.data.isNotEmpty().let {
            payload = message.data
        }

        createNotificationChannel()
        // รหัสเฉพาะเพื่อแสดงการแจ้งเตือนใหม่ทุกครั้งที่เราคลิกการแจ้งเตือน หากคุณต้องการแทนที่ครั้งก่อน ให้ใช้ค่าคงที่เป็นรหัส
        val date = Date()
        val notificationId = SimpleDateFormat("ddHHmmss", Locale.US).format(date).toInt()

        // จัดการการคลิกการแจ้งเตือน เริ่ม EkycActivity โดยแตะการแจ้งเตือน
        val mainIntent = Intent(this, EkycActivity::class.java)

        // หากคุณต้องการส่งข้อมูลในการแจ้งเตือนและเข้าสู่กิจกรรมที่จำเป็น
        mainIntent.putExtra("KEY_NAME", "Admin Jade")
        mainIntent.putExtra("KEY_EMAIL", "it@idhomr2015.com")
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val mainPendingIntent = PendingIntent.getActivity(this, 1, mainIntent, PendingIntent.FLAG_IMMUTABLE)

        // การสร้างเครื่องมือสร้างการแจ้งเตือน
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)  // ลำดับความสำคัญของการแจ้งเตือน
            .setAutoCancel(true)  // cancel notification on click
            .setContentIntent(mainPendingIntent)  // เพิ่มความตั้งใจในการคลิก

        // ผู้จัดการการแจ้งเตือน
        val notificationManagerCompat = NotificationManagerCompat.from(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManagerCompat.notify(notificationId, notificationBuilder.build())
        }
    }

    private fun createNotificationChannel() {
        val name: CharSequence = "ไอดีโฮมการแจ้งเตือน"
        val description = "การแจ้งเตือนจากแอปไอดีโฮม"
        // ความสำคัญของการแจ้งเตือนของคุณ
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
        notificationChannel.description = description
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private companion object {
        private const val TAG = "MyFirebaseMsgService"
        private const val CHANNEL_ID = "channel01"
    }
}