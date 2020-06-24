package io.rot.labs.projectconf.ui.main

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.utils.common.Event
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class MainViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkDBHelper: NetworkDBHelper
) : BaseViewModel(schedulerProvider, compositeDisposable, networkDBHelper) {

    companion object {
        private const val UPCOMING = "upcoming"
        private const val ALERTS = "alerts"
        private const val BOOKMARKS = "bookmarks"
    }

    val upComingNavigation = MutableLiveData<Event<Boolean>>()
    val archiveNavigation = MutableLiveData<Event<Boolean>>()
    val bookmarksNavigation = MutableLiveData<Event<Boolean>>()
    val alertsNavigation = MutableLiveData<Event<Boolean>>()
    val changeThemeNavigation = MutableLiveData<Event<Boolean>>()
    val aboutNavigation = MutableLiveData<Event<Boolean>>()

    private val activeFragmentNavigation = MutableLiveData(UPCOMING)

    override fun onCreate() {
        onActiveFragmentRedirection()
    }

    fun onUpComingRedirection() {
        upComingNavigation.postValue(Event(true))
        activeFragmentNavigation.postValue(UPCOMING)
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

    fun onChangeTheme() {
        changeThemeNavigation.postValue(Event(true))
    }

    fun onAboutRedirection() {
        aboutNavigation.postValue(Event(true))
    }

    private fun onActiveFragmentRedirection() {
        when (activeFragmentNavigation.value) {
            UPCOMING -> onUpComingRedirection()
            BOOKMARKS -> onBookmarksRedirection()
            ALERTS -> onAlertsRedirection()
        }
    }
}
