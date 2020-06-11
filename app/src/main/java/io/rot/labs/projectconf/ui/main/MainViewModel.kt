package io.rot.labs.projectconf.ui.main

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.utils.common.Event
import io.rot.labs.projectconf.utils.network.NetworkHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class MainViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    companion object {
        private const val UPCOMING = "upcoming"
        private const val TWEETS = "tweets"
        private const val ALERTS = "alerts"
        private const val BOOKMARKS = "bookmarks"
    }

    val upComingNavigation = MutableLiveData<Event<Boolean>>()
    val tweetNavigation = MutableLiveData<Event<Boolean>>()
    val archiveNavigation = MutableLiveData<Event<Boolean>>()
    val bookmarksNavigation = MutableLiveData<Event<Boolean>>()
    val alertsNavigation = MutableLiveData<Event<Boolean>>()
    val settingsNavigation = MutableLiveData<Event<Boolean>>()
    val aboutNavigation = MutableLiveData<Event<Boolean>>()

    private val activeFragmentNavigation = MutableLiveData(UPCOMING)

    override fun onCreate() {
        onActiveFragmentRedirection()
    }

    fun onUpComingRedirection() {
        upComingNavigation.postValue(Event(true))
        activeFragmentNavigation.postValue(UPCOMING)
    }

    fun onTweetRedirection() {
        tweetNavigation.postValue(Event(true))
        activeFragmentNavigation.postValue(TWEETS)
    }

    fun onArchiveRedirection() {
        archiveNavigation.postValue(Event(true))
    }

    fun onBookmarksRedirection() {
        bookmarksNavigation.postValue(Event(true))
        activeFragmentNavigation.postValue(BOOKMARKS)
    }

    fun onAlertsRedirection() {
        alertsNavigation.postValue(Event(true))
        activeFragmentNavigation.postValue(ALERTS)
    }

    fun onSettingsRedirection() {
        settingsNavigation.postValue(Event(true))
    }

    fun onAboutRedirection() {
        aboutNavigation.postValue(Event(true))
    }

    private fun onActiveFragmentRedirection() {
        when (activeFragmentNavigation.value) {
            UPCOMING -> onUpComingRedirection()
            TWEETS -> onTweetRedirection()
            BOOKMARKS -> onBookmarksRedirection()
            ALERTS -> onAlertsRedirection()
        }
    }
}
