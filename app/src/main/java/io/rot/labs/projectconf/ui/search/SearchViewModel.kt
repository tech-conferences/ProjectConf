/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.search

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class SearchViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val compositeDisposable: CompositeDisposable,
    networkDBHelper: NetworkDBHelper,
    private val eventsRepository: EventsRepository

) : BaseViewModel(schedulerProvider, compositeDisposable, networkDBHelper) {

    private val eventsHolder = MutableLiveData<List<EventEntity>>()
    val events = eventsHolder

    val nameQueryHolder = MutableLiveData<String>()

    val progress = MutableLiveData<Boolean>()

    override fun onCreate() {
    }

    fun getRecentEventsByQuery(nameQuery: String, yearList: List<Int>): Observable<String> {

        if (nameQuery.isEmpty()) {
            nameQueryHolder.postValue(nameQuery)
            return Observable.just(nameQuery)
        }

        progress.postValue(true)
        if (eventsHolder.value?.isNotEmpty() == true && nameQuery == nameQueryHolder.value) {
            progress.postValue(false)
            return Observable.just(nameQuery)
        }
        compositeDisposable.add(
            eventsRepository.getRecentEventsByQuery(nameQuery, yearList)
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    nameQueryHolder.postValue(nameQuery)
                    events.postValue(it)
                    progress.postValue(false)
                }, {
                    progress.postValue(false)
                    handleNetworkDBError(it)
                })
        )
        return Observable.just(nameQuery)
    }
}
