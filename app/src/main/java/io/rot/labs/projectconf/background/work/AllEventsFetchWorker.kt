/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.background.work

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Single
import io.rot.labs.projectconf.data.repository.EventsRepository
import timber.log.Timber

class AllEventsFetchWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val eventsRepository: EventsRepository
) : RxWorker(appContext, workerParams) {

    companion object {
        const val TAG = "AllEventsFetchWorker"
    }

    override fun createWork(): Single<Result> {
        return eventsRepository.getUpComingEventsForCurrentYear()
            .map {
                Result.success()
            }
            .onErrorReturn {
                Timber.d(it)
                return@onErrorReturn Result.retry()
            }
    }
}
