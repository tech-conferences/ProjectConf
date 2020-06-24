package io.rot.labs.projectconf.ui.eventDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.local.db.entity.BookmarkedEvent
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.repository.BookmarksRepository
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider
import java.util.Date

class EventDetailsViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val compositeDisposable: CompositeDisposable,
    networkDBHelper: NetworkDBHelper,
    private val eventsRepository: EventsRepository,
    private val bookmarksRepository: BookmarksRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkDBHelper) {

    private val eventDetailsHolder = MutableLiveData<EventEntity>()

    val eventDetails: LiveData<EventEntity> = eventDetailsHolder

    val progress = MutableLiveData<Boolean>()

    val isBookmarked = MutableLiveData<Boolean>()

    val cfpScheduledId = MutableLiveData<Int?>()

    override fun onCreate() {}

    fun getEventDetails(name: String, startDate: Date, topic: String) {
        progress.postValue(true)
        if (eventDetailsHolder.value != null) {
            progress.postValue(false)
            return
        }
        compositeDisposable.add(
            eventsRepository.getEventDetails(name, startDate, topic)
                .subscribeOn(schedulerProvider.io())
                .subscribe(
                    {
                        eventDetailsHolder.postValue(it)
                        progress.postValue(false)
                    }, {
                        progress.postValue(false)
                        handleNetworkDBError(it)
                    }
                )
        )
    }

    fun checkIfBookmarked(name: String, startDate: Date, topic: String) {
        compositeDisposable.add(
            bookmarksRepository.getBookmarkedEvent(name, startDate, topic)
                .isEmpty
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    isBookmarked.postValue(!it)
                }, {
                    handleNetworkDBError(it)
                })
        )
    }

    fun checkIfCFPScheduled(name: String, startDate: Date, topic: String) {
        compositeDisposable.add(
            bookmarksRepository.getBookmarkedEvent(name, startDate, topic)
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    cfpScheduledId.postValue(it.cfpReminderId)
                }, {
                    handleNetworkDBError(it)
                })
        )
    }

    fun insertBookmarkedEvent(bookmarkedEvent: BookmarkedEvent) {
        compositeDisposable.add(
            bookmarksRepository.insertBookmarkEvent(bookmarkedEvent)
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    isBookmarked.postValue(true)
                    cfpScheduledId.postValue(bookmarkedEvent.cfpReminderId)
                }, {
                    handleNetworkDBError(it)
                })
        )
    }

    fun removeBookmarkedEvent(bookmarkedEvent: BookmarkedEvent) {
        compositeDisposable.add(
            bookmarksRepository.removeBookmarkEvent(bookmarkedEvent)
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    isBookmarked.postValue(false)
                    cfpScheduledId.postValue(null)
                }, {
                    handleNetworkDBError(it)
                })
        )
    }
}
