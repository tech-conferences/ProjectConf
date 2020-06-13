package io.rot.labs.projectconf.ui.eventDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.utils.network.NetworkHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider
import java.util.Date

class EventDetailsViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val eventsRepository: EventsRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    private val eventDetailsHolder = MutableLiveData<EventEntity>()

    val eventDetails: LiveData<EventEntity> = eventDetailsHolder

    val progress = MutableLiveData<Boolean>()

    override fun onCreate() {}

    fun getEventDetails(name: String, startDate: Date) {
        progress.postValue(true)
        if (eventDetailsHolder.value != null) {
            progress.postValue(false)
            return
        }
        compositeDisposable.add(
            eventsRepository.getEventDetails(name, startDate)
                .subscribeOn(schedulerProvider.io())
                .subscribe(
                    {
                        eventDetailsHolder.postValue(it)
                        progress.postValue(false)
                    }, {
                        progress.postValue(false)
                        handleNetworkError(it)
                    }
                )
        )
    }
}
