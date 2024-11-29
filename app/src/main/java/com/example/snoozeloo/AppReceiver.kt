package com.example.snoozeloo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import timber.log.Timber

class AppReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("onReceive : $intent")
        val bundle = intent.extras
        if (bundle != null) {
            val name = bundle.getString("EXTRA_NAME")
            val timeHours = bundle.getInt("EXTRA_TIME_HOURS")
            val timeMinutes = bundle.getInt("EXTRA_TIME_MINUTES")
            val alarmURI = bundle.getString("EXTRA_ALARM_RINGTONE_URI")
            Timber.d("Alarm triggered : $name, $timeHours:$timeMinutes, $alarmURI")
            showFullScreenNotification(context, intent.extras)
        }

    }

    fun showFullScreenNotification(context: Context, extras: Bundle?) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create Notification Channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "fullscreen_channel",
                "Full Screen Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "This channel is used for full screen notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Intent to launch the full-screen activity
        val fullScreenIntent = Intent(context, FullScreenActivity::class.java).apply {
            if (extras != null) {
                putExtras(extras)
            }
        }
        val fullScreenPendingIntent = PendingIntent.getActivity(
            context,
            0,
            fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification
        val notification = NotificationCompat.Builder(context, "fullscreen_channel")
            .setContentTitle("Full Screen Alert")
            .setContentText("This is a full screen notification.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setAutoCancel(true)
            .build()

        // Show the notification
        NotificationManagerCompat.from(context).notify(1, notification)
    }
}