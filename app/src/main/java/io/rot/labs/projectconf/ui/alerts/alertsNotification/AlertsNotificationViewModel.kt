/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.alerts.alertsNotification

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.model.EventItem
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.ui.eventsItem.EventsItemHelper
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class AlertsNotificationViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkDBHelper: NetworkDBHelper
) : BaseViewModel(schedulerProvider, compositeDisposable, networkDBHelper) {

    val events = MutableLiveData<List<EventItem>>()

    override fun onCreate() {}

    fun getEvents(list: List<EventEntity>) {
        if (events.value?.isNotEmpty() == true) {
            return
        }
        events.postValue(EventsItemHelper.transformToInterleavedList(list))
    }
}
