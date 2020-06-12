package io.rot.labs.projectconf.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.model.EventItem
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.ui.eventsItem.EventsItemHelper.transformToInterleavedList
import io.rot.labs.projectconf.utils.network.NetworkHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class UpComingViewModel(
    private val schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val eventsRepository: EventsRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

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
        eventsRepository.getUpComingEventsForCurrentMonth(isRefresh)
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                upcomingEventsHolder.postValue(transformToInterleavedList(it))
                progress.postValue(false)
            }, {
                progress.postValue(false)
                handleNetworkError(it)
            })
    }
}
