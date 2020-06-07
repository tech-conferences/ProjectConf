package io.rot.labs.projectconf.ui.upcoming

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.model.EventBase
import io.rot.labs.projectconf.data.model.EventHeader
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.network.NetworkHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class UpComingViewModel(
    private val schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val eventsRepository: EventsRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val upcomingEvents = MutableLiveData<List<EventBase>>()

    val progress = MutableLiveData<Boolean>()

    override fun onCreate() {
        getUpComingEventsForCurrentMonth()
    }

    fun getUpComingEventsForCurrentMonth() {
        progress.postValue(true)
        eventsRepository.getUpComingEventsForCurrentMonth()
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                upcomingEvents.postValue(transformToInterleavedList(it))
                progress.postValue(false)
            }, {
                handleNetworkError(it)
                progress.postValue(false)
            })
    }

    private fun transformToInterleavedList(list: List<EventEntity>): List<EventBase> {
        val periodEventListMap = mutableMapOf<String, MutableList<EventEntity>>()

        list.forEach {
            val eventPeriod = TimeDateUtils.getEventPeriod(it.event.startDate)
            var eventList = periodEventListMap[eventPeriod]
            if (eventList == null) {
                eventList = mutableListOf()
                eventList.add(it)
                periodEventListMap[eventPeriod] = eventList
            } else {
                eventList.add(it)
            }
        }

        val result = mutableListOf<EventBase>()

        periodEventListMap.forEach {
            result.add(EventHeader(it.key))
            it.value.forEach { event ->
                result.add(event)
            }
        }

        return result
    }
}