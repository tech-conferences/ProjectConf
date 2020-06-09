package io.rot.labs.projectconf.ui.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.model.EventBase
import io.rot.labs.projectconf.data.model.EventHeader
import io.rot.labs.projectconf.ui.base.BaseItemViewModel
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.network.NetworkHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider
import javax.inject.Inject

class EventBaseViewModel @Inject constructor(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseItemViewModel<EventBase>(schedulerProvider, compositeDisposable, networkHelper) {

    val name: LiveData<String?> = Transformations.map(data) {
        if (it is EventEntity) {
            it.event.name
        } else {
            null
        }
    }

    val topic: LiveData<String?> = Transformations.map(data) {
        if (it is EventEntity) {
            it.topic
        } else {
            null
        }
    }

    val place: LiveData<String?> = Transformations.map(data) {
        if (it is EventEntity) {
            if (it.event.country == "Online") {
                it.event.country
            } else {
                "${it.event.city}, ${it.event.country}"
            }
        } else {
            null
        }
    }

    val day: LiveData<String?> = Transformations.map(data) {
        if (it is EventEntity) {
            TimeDateUtils.getFormattedDay(it.event.startDate)
        } else {
            null
        }
    }

    val eventPeriod: LiveData<String?> = Transformations.map(data) {
        if (it is EventHeader) {
            it.period
        } else {
            null
        }
    }

    override fun onCreate() {
    }
}
