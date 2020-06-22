package io.rot.labs.projectconf.utils.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.work.UserTopicNewAlertsWorker
import io.rot.labs.projectconf.ui.alerts.alertsNotification.AlertsViewActivity

object NotificationUtils {

    @JvmStatic
    fun generateNotificationEventAlerts(
        appContext: Context,
        list: List<EventEntity>,
        channelId: String,
        notificationTitle: String,
        notificationText: String,
        notificationId: Int
    ) {
        val longText = list.joinToString("\n") {
            "${it.topic} : ${it.event.name}"
        }

        val alertsList = arrayListOf<EventEntity>().apply {
            addAll(list)
        }

        val intent = Intent(appContext, AlertsViewActivity::class.java).apply {
            putParcelableArrayListExtra(AlertsViewActivity.ALERTS_LIST, alertsList)

            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent =
            PendingIntent.getActivity(appContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        createNotificationChannel(appContext)

        val notification =
            NotificationCompat.Builder(appContext, channelId)
                .setSmallIcon(R.drawable.android_logo)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(longText)
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        NotificationManagerCompat.from(appContext)
            .notify(notificationId, notification)
    }

    @JvmStatic
    fun createNotificationChannel(appContext: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = appContext.getString(R.string.channel_name)
            val descriptionText = appContext.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(UserTopicNewAlertsWorker.CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
            val notificationManager: NotificationManager =
                appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
