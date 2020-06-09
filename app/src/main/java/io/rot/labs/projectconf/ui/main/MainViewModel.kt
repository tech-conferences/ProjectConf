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

    val upComingNavigation = MutableLiveData<Event<Boolean>>()
    val tweetNavigation = MutableLiveData<Event<Boolean>>()
    val archiveNavigation = MutableLiveData<Event<Boolean>>()
    val bookmarksNavigation = MutableLiveData<Event<Boolean>>()
    val alertsNavigation = MutableLiveData<Event<Boolean>>()
    val settingsNavigation = MutableLiveData<Event<Boolean>>()
    val aboutNavigation = MutableLiveData<Event<Boolean>>()

    override fun onCreate() {
        onUpComingRedirection()
    }

    fun onUpComingRedirection() {
        upComingNavigation.postValue(Event(true))
    }

    fun onTweetRedirection() {
        tweetNavigation.postValue(Event(true))
    }

    fun onArchiveRedirection() {
        archiveNavigation.postValue(Event(true))
    }

    fun onBookmarksRedirection() {
        bookmarksNavigation.postValue(Event(true))
    }

    fun onAlertsRedirection() {
        alertsNavigation.postValue(Event(true))
    }

    fun onSettingsRedirection() {
        settingsNavigation.postValue(Event(true))
    }

    fun onAboutRedirection() {
        aboutNavigation.postValue(Event(true))
    }
}
