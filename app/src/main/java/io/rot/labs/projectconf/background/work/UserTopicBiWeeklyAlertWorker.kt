package io.rot.labs.projectconf.background.work

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Single
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.data.local.prefs.UserTopicPreferences
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.utils.notification.NotificationUtils

class UserTopicBiWeeklyAlertWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val userTopicPrefs: UserTopicPreferences,
    private val eventsRepository: EventsRepository
) : RxWorker(appContext, workerParams) {

    companion object {
        const val TAG = "UserTopicBiWeeklyAlertsWorker"
        const val CHANNEL_ID = "User Topics BiWeekly Alerts"
        const val notificationId = 3
    }

    override fun createWork(): Single<Result> {
        val userTopics = userTopicPrefs.getUserTopics()
        return if (userTopics != null) {
            eventsRepository.getUpComingEventsForUserTechCurrentMonth(userTopics)
                .map {
                    if (it.isNotEmpty()) {
                        NotificationUtils.generateNotificationEventAlerts(
                            applicationContext,
                            it,
                            CHANNEL_ID,
                            applicationContext.getString(R.string.monthly_alerts_title),
                            applicationContext.getString(R.string.conferences_that_might_interests),
                            notificationId
                        )
                    }
                    Result.success()
                }
                .onErrorReturn {
                    return@onErrorReturn Result.retry()
                }
        } else {
            Single.just(Result.retry())
        }
    }
}
