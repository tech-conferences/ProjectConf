package io.rot.labs.projectconf.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.ui.archive.ArchiveViewModel
import io.rot.labs.projectconf.ui.base.BaseActivity
import io.rot.labs.projectconf.ui.eventDetails.EventDetailsViewModel
import io.rot.labs.projectconf.ui.eventsItem.EventsItemAdapter
import io.rot.labs.projectconf.ui.eventsList.EventsListViewModel
import io.rot.labs.projectconf.ui.main.MainViewModel
import io.rot.labs.projectconf.ui.search.SearchViewModel
import io.rot.labs.projectconf.ui.settings.SettingsViewModel
import io.rot.labs.projectconf.utils.ViewModelProviderFactory
import io.rot.labs.projectconf.utils.network.NetworkHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun provideLinearLayoutManager() = LinearLayoutManager(activity)

    @Provides
    fun provideGridLayoutManager(): GridLayoutManager = GridLayoutManager(activity, 2)

    @Provides
    fun provideEventAdapter(): EventsItemAdapter =
        EventsItemAdapter(activity.lifecycle, ArrayList())

    @Provides
    fun provideMainViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): MainViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(schedulerProvider, compositeDisposable, networkHelper)
        }).get(MainViewModel::class.java)
    }

    @Provides
    fun provideArchiveViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): ArchiveViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(ArchiveViewModel::class) {
            ArchiveViewModel(schedulerProvider, compositeDisposable, networkHelper)
        }).get(ArchiveViewModel::class.java)
    }

    @Provides
    fun provideSettingsViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): SettingsViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(SettingsViewModel::class) {
            SettingsViewModel(schedulerProvider, compositeDisposable, networkHelper)
        }).get(SettingsViewModel::class.java)
    }

    @Provides
    fun provideSearchViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): SearchViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(SearchViewModel::class) {
            SearchViewModel(schedulerProvider, compositeDisposable, networkHelper)
        }).get(SearchViewModel::class.java)
    }

    @Provides
    fun provideEventsListViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        eventsRepository: EventsRepository
    ): EventsListViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(EventsListViewModel::class) {
            EventsListViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper,
                eventsRepository
            )
        }).get(EventsListViewModel::class.java)
    }

    @Provides
    fun provideEventDetailsViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        eventsRepository: EventsRepository
    ): EventDetailsViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(EventDetailsViewModel::class) {
            EventDetailsViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper,
                eventsRepository
            )
        }).get(EventDetailsViewModel::class.java)
    }
}
