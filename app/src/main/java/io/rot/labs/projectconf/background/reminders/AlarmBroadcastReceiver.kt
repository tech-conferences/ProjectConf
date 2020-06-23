package io.rot.labs.projectconf.background.reminders

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.rot.labs.projectconf.ConfApplication
import io.rot.labs.projectconf.data.local.db.entity.BookmarkedEvent
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.repository.BookmarksRepository
import io.rot.labs.projectconf.di.component.DaggerBroadcastReceiverComponent
import io.rot.labs.projectconf.ui.eventDetails.eventReminder.EventReminderDialogFragment
import io.rot.labs.projectconf.ui.eventDetails.eventReminder.EventReminderDialogFragment.Companion.reminderChannelId
import io.rot.labs.projectconf.utils.notification.NotificationUtils
import io.rot.labs.projectconf.utils.rx.SchedulerProvider
import javax.inject.Inject
import kotlin.random.Random
import timber.log.Timber

class AlarmBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var bookmarksRepository: BookmarksRepository

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @SuppressLint("CheckResult")
    override fun onReceive(context: Context?, intent: Intent?) {

        injectDependencies(context!!)

        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            bookmarksRepository.getCFPReminderEvents()
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    setupAlarms(context, it)
                }, {
                    Timber.e(it)
                })
        } else {
            intent?.let {
                val bundle = it.getBundleExtra(EventReminderDialogFragment.EVENT_BUNDLE)
                bundle?.let {
                    val eventEntity =
                        bundle.get(EventReminderDialogFragment.EVENT_REMINDER) as EventEntity

                    bookmarksRepository.insertBookmarkEvent(BookmarkedEvent(eventEntity))
                        .subscribeOn(schedulerProvider.io())
                        .subscribe({
                            Timber.d("CFP Notified and Updated")
                        }, { err ->
                            Timber.e(err)
                        })

                    val randNotificationId = Random.nextInt(0, Int.MAX_VALUE)
                    NotificationUtils.generateCFPReminder(
                        context,
                        eventEntity,
                        reminderChannelId,
                        randNotificationId
                    )
                }
            }
        }
    }

    private fun setupAlarms(context: Context, cfpReminders: List<BookmarkedEvent>) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        cfpReminders.forEach {
            val alarmIntent = Intent(context, AlarmBroadcastReceiver::class.java).let { intent ->
                val bundle = Bundle()
                bundle.putParcelable(EventReminderDialogFragment.EVENT_REMINDER, it.eventEntity)
                intent.putExtra(EventReminderDialogFragment.EVENT_BUNDLE, bundle)

                PendingIntent.getBroadcast(
                    context,
                    it.cfpReminderId!!,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                it.cfpReminderTimeMillis!!,
                alarmIntent
            )
        }
    }

    private fun injectDependencies(context: Context) {
        val buildComponent = DaggerBroadcastReceiverComponent.builder()
            .applicationComponent((context.applicationContext as ConfApplication).appComponent)
            .build()
        buildComponent.inject(this)
    }
}
