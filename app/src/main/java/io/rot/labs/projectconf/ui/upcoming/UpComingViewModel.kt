/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.model.EventItem
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.ui.eventsItem.EventsItemHelper.transformToInterleavedList
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider
import java.util.concurrent.TimeUnit

class UpComingViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val compositeDisposable: CompositeDisposable,
    networkDBHelper: NetworkDBHelper,
    private val eventsRepository: EventsRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkDBHelper) {

    private val upcomingEventsHolder = MutableLiveData<List<EventItem>>()
    val upcomingEvents: LiveData<List<EventItem>> = upcomingEventsHolder

    val progress = MutableLiveData<Boolean>()

    override fun onCreate() {
        getUpComingEventsForCurrentMonth()
    }

    fun getUpComingEventsForCurrentMonth(isRefresh: Boolean = false) {
        progress.postValue(true)
        if (upcomingEventsHolder.value?.isNotEmpty() == true && !isRefresh) {
            progress.postValue(false)
            return
        }
        compositeDisposable.add(
            eventsRepository.getUpComingEventsForCurrentMonth(isRefresh)
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    upcomingEventsHolder.postValue(transformToInterleavedList(it))
                    progress.postValue(false)
                }, {
                    progress.postValue(false)
                    handleNetworkDBError(it)
                })
        )
    }
}
