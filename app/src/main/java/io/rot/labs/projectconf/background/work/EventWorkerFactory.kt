/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.background.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import io.rot.labs.projectconf.data.local.prefs.UserTopicPreferences
import io.rot.labs.projectconf.data.repository.EventsRepository

class EventWorkerFactory(
    private val eventsRepository: EventsRepository,
    private val userTopicPreferences: UserTopicPreferences
) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            AllEventsFetchWorker::class.java.name -> AllEventsFetchWorker(
                appContext,
                workerParameters,
                eventsRepository
            )
            UserTopicNewAlertsWorker::class.java.name -> UserTopicNewAlertsWorker(
                appContext,
                workerParameters,
                userTopicPreferences,
                eventsRepository
            )
            UserTopicBiWeeklyAlertWorker::class.java.name -> UserTopicBiWeeklyAlertWorker(
                appContext,
                workerParameters,
                userTopicPreferences,
                eventsRepository
            )
            else -> null
        }
    }
}
