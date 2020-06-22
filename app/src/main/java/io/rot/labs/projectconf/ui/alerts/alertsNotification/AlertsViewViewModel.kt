package io.rot.labs.projectconf.ui.alerts.alertsNotification

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.model.EventItem
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.ui.eventsItem.EventsItemHelper
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class AlertsViewViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val compositeDisposable: CompositeDisposable,
    networkDBHelper: NetworkDBHelper,
    private val eventsRepository: EventsRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkDBHelper) {

    val events = MutableLiveData<List<EventItem>>()

    override fun onCreate() {}

    fun getEvents(list: List<EventEntity>) {
        if (events.value?.isNotEmpty() == true) {
            return
        }
        val source = makeSourceList(list)
        compositeDisposable.add(
            Single.merge(source).collect(
                { mutableListOf<EventEntity>() },
                { collector, value ->
                    collector.add(value)
                }
            ).subscribeOn(schedulerProvider.io())
                .subscribe({
                    Log.d("PUI", "alertList ${it.size}")
                    events.postValue(EventsItemHelper.transformToInterleavedList(it))
                }, {
                    handleNetworkDBError(it)
                })
        )
    }

    private fun makeSourceList(list: List<EventEntity>): List<Single<EventEntity>> {
        val mutableList = mutableListOf<Single<EventEntity>>()
        list.forEach {
            mutableList.add(
                eventsRepository.getEventDetails(
                    it.event.name,
                    it.event.startDate,
                    it.topic
                )
            )
        }
        return mutableList
    }
}
