/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.utils.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.background.cfpUrl.CFPUrlReceiver
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.ui.alerts.alertsNotification.AlertsNotificationActivity
import io.rot.labs.projectconf.ui.eventDetails.EventDetailsActivity
import kotlin.math.absoluteValue
import kotlin.random.Random

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

        val intent = Intent(appContext, AlertsNotificationActivity::class.java).apply {
            putParcelableArrayListExtra(AlertsNotificationActivity.ALERTS_LIST, alertsList)

            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent =
            PendingIntent.getActivity(appContext, Random.nextInt().absoluteValue, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        createNotificationChannel(appContext, channelId)

        val notification =
            NotificationCompat.Builder(appContext, channelId)
                .setSmallIcon(R.drawable.ic_alerts_notify)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(longText)
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(appContext, R.color.yellow500))
                .setAutoCancel(true)
                .build()

        NotificationManagerCompat.from(appContext)
            .notify(notificationId, notification)
    }

    @JvmStatic
    fun generateCFPReminder(
        context: Context,
        eventEntity: EventEntity,
        channelId: String,
        notificationId: Int
    ) {
        val intent = Intent(context, EventDetailsActivity::class.java).apply {
            putExtra(EventDetailsActivity.EVENT_NAME, eventEntity.event.name)
            putExtra(EventDetailsActivity.EVENT_START_DATE, eventEntity.event.startDate.time)
            putExtra(EventDetailsActivity.EVENT_TOPIC, eventEntity.topic)

            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val randReqCode = Random.nextInt(0, Int.MAX_VALUE)
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                randReqCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        createNotificationChannel(context, channelId)

        val notification =
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_alerts_notify)
                .setContentTitle(context.getString(R.string.cfp_reminder))
                .setContentText("${eventEntity.topic} : ${eventEntity.event.name}")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, R.color.yellow500))
                .setAutoCancel(true)

        eventEntity.event.cfpUrl?.let {
            val cfpUrlIntent = Intent(context, CFPUrlReceiver::class.java).let { intent ->
                intent.putExtra(CFPUrlReceiver.URL_KEY, it)
                intent.putExtra(CFPUrlReceiver.NOTIFY_KEY, notificationId)
                PendingIntent.getBroadcast(
                    context,
                    Random.nextInt().absoluteValue,
                    intent,
                    PendingIntent.FLAG_CANCEL_CURRENT
                )
            }

            notification.addAction(
                R.drawable.ic_web,
                context.getString(R.string.go_to_cfp_url),
                cfpUrlIntent
            )
        }

        val siteIntent = Intent(context, CFPUrlReceiver::class.java).let {
            it.putExtra(CFPUrlReceiver.URL_KEY, eventEntity.event.url)
            it.putExtra(CFPUrlReceiver.NOTIFY_KEY, notificationId)
            PendingIntent.getBroadcast(
                context,
                Random.nextInt().absoluteValue,
                it,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
        }
        notification.addAction(
            R.drawable.ic_web,
            context.getString(R.string.go_to_site),
            siteIntent
        )

        NotificationManagerCompat.from(context)
            .notify(notificationId, notification.build())
    }

    @JvmStatic
    fun createNotificationChannel(appContext: Context, channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = appContext.getString(R.string.channel_name)
            val descriptionText = appContext.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(channelId, name, importance).apply {
                    description = descriptionText
                }
            val notificationManager: NotificationManager =
                appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
