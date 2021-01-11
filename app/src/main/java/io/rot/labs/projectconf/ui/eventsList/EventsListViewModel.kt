/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.eventsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.model.EventItem
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.ui.eventsItem.EventsItemHelper
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class EventsListViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val compositeDisposable: CompositeDisposable,
    networkDBHelper: NetworkDBHelper,
    private val eventsRepository: EventsRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkDBHelper) {

    private val upcomingEventsHolder = MutableLiveData<List<EventItem>>()
    val upcomingEvents: LiveData<List<EventItem>> = upcomingEventsHolder

    private val archiveEventsHolder = MutableLiveData<List<EventItem>>()
    val archiveEvents: LiveData<List<EventItem>> = archiveEventsHolder

    val progress = MutableLiveData<Boolean>()

    override fun onCreate() {}

    fun getUpComingEventsForTech(topics: List<String>, isRefresh: Boolean = false) {
        progress.postValue(true)
        if (upcomingEventsHolder.value?.isNotEmpty() == true && !isRefresh) {
            progress.postValue(false)
            return
        }
        compositeDisposable.add(
            eventsRepository.getUpComingEventsForTech(topics, isRefresh)
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    upcomingEventsHolder.postValue(EventsItemHelper.transformToInterleavedList(it))
                    progress.postValue(false)
                }, {
                    progress.postValue(false)
                    handleNetworkDBError(it)
                })
        )
    }

    fun getEventsForYearAndTech(year: Int, tech: String, isRefresh: Boolean = false) {
        progress.postValue(true)
        if (archiveEventsHolder.value?.isNotEmpty() == true && !isRefresh) {
            progress.postValue(false)
            return
        }
        compositeDisposable.add(
            eventsRepository.getEventsForYearAndTech(tech, year, isRefresh)
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    archiveEventsHolder.postValue(EventsItemHelper.transformToInterleavedList(it))
                    progress.postValue(false)
                }, {
                    progress.postValue(false)
                    handleNetworkDBError(it)
                })
        )
    }
}
