package io.rot.labs.projectconf.data.work

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Single
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.data.local.prefs.UserTopicPreferences
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.utils.notification.NotificationUtils
import timber.log.Timber

class UserTopicNewAlertsWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val userTopicPrefs: UserTopicPreferences,
    private val eventsRepository: EventsRepository
) : RxWorker(appContext, workerParams) {

    companion object {
        const val TAG = "UserTopicNewAlertsWorker"
        const val CHANNEL_ID = "User Topic New Alerts"
        const val notificationId = 21
    }

    override fun createWork(): Single<Result> {
        val userTopics = userTopicPrefs.getUserTopics()
        return if (userTopics != null) {
            eventsRepository.getUpcomingEventsForUserTech(userTopics)
                .map {
                    if (it.isNotEmpty()) {
                        NotificationUtils.generateNotificationEventAlerts(
                            applicationContext,
                            it,
                            CHANNEL_ID,
                            applicationContext.getString(R.string.new_alerts_title),
                            applicationContext.getString(R.string.new_conferences),
                            notificationId
                        )
                    }
                    Result.success()
                }
                .onErrorReturn {
                    Timber.d(it)
                    return@onErrorReturn Result.retry()
                }
        } else {
            Single.just(Result.retry())
        }
    }
}
