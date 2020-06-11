package io.rot.labs.projectconf.ui.eventsList

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.model.EventItem
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.ui.eventsItem.EventsItemHelper
import io.rot.labs.projectconf.utils.network.NetworkHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class EventsListViewModel(
    private val schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val eventsRepository: EventsRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val upcomingEvents = MutableLiveData<List<EventItem>>()

    val archiveEvents = MutableLiveData<List<EventItem>>()

    val progress = MutableLiveData<Boolean>()

    override fun onCreate() {}

    fun getUpComingEventsForTech(topics: List<String>, isRefresh: Boolean = false) {
        progress.postValue(true)
        eventsRepository.getUpComingEventsForTech(topics, isRefresh)
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                upcomingEvents.postValue(EventsItemHelper.transformToInterleavedList(it))
                progress.postValue(false)
            }, {
                progress.postValue(false)
                handleNetworkError(it)
            })
    }

    fun getEventsForYearAndTech(year: Int, tech: String, isRefresh: Boolean = false) {
        progress.postValue(true)
        eventsRepository.getEventsForYearAndTech(tech, year, isRefresh)
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                archiveEvents.postValue(EventsItemHelper.transformToInterleavedList(it))
                progress.postValue(false)
            }, {
                progress.postValue(false)
                handleNetworkError(it)
            })
    }
}
