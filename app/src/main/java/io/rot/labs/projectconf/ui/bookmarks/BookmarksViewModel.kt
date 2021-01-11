/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.bookmarks

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.model.EventItem
import io.rot.labs.projectconf.data.repository.BookmarksRepository
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.ui.eventsItem.EventsItemHelper
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class BookmarksViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val compositeDisposable: CompositeDisposable,
    networkDBHelper: NetworkDBHelper,
    private val bookmarksRepository: BookmarksRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkDBHelper) {

    val bookmarkedEvents = MutableLiveData<List<EventItem>>()
    val cfpReminders = MutableLiveData<List<EventItem>>()
    val isCFPFilter = MutableLiveData<Boolean>()

    override fun onCreate() {
        if (isCFPFilter.value == true) {
            getCFPReminderEvents()
        } else {
            getAllBookmarks()
        }
    }

    fun onCFPFilterChange(value: Boolean) {
        isCFPFilter.postValue(value)
    }

    fun getAllBookmarks() {
        compositeDisposable.add(
            bookmarksRepository.getAllBookmarkedEvents()
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    val eventEntityList = it.map { bookmarkedEvent ->
                        bookmarkedEvent.eventEntity
                    }
                    bookmarkedEvents.postValue(
                        EventsItemHelper.transformToInterleavedList(
                            eventEntityList
                        )
                    )
                }, {
                    handleNetworkDBError(it)
                })
        )
    }

    fun getCFPReminderEvents() {
        compositeDisposable.add(
            bookmarksRepository.getCFPReminderEvents()
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    val eventEntityList = it.map { bookmarkedEvent ->
                        bookmarkedEvent.eventEntity
                    }
                    cfpReminders.postValue(
                        EventsItemHelper.transformToInterleavedList(
                            eventEntityList
                        )
                    )
                }, {
                })
        )
    }
}
