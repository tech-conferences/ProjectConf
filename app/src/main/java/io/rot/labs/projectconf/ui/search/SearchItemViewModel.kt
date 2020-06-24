package io.rot.labs.projectconf.ui.search

import androidx.lifecycle.Transformations
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.ui.base.BaseItemViewModel
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider
import javax.inject.Inject

class SearchItemViewModel @Inject constructor(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkDBHelper: NetworkDBHelper
) : BaseItemViewModel<EventEntity>(schedulerProvider, compositeDisposable, networkDBHelper) {

    val name = Transformations.map(data) {
        it.event.name
    }

    val place = Transformations.map(data) {
        if (it.event.country == "Online") {
            it.event.country
        } else {
            "${it.event.city}, ${it.event.country}"
        }
    }

    val startDate = Transformations.map(data) {
        it.event.startDate
    }

    val topic = Transformations.map(data) {
        it.topic
    }

    override fun onCreate() {
    }
}
