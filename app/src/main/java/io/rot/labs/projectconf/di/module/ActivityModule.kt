package io.rot.labs.projectconf.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.ui.allTech.AllTechAdapter
import io.rot.labs.projectconf.ui.allTech.AllTechViewModel
import io.rot.labs.projectconf.ui.archive.ArchiveAdapter
import io.rot.labs.projectconf.ui.archive.ArchiveViewModel
import io.rot.labs.projectconf.ui.base.BaseActivity
import io.rot.labs.projectconf.ui.eventDetails.EventDetailsViewModel
import io.rot.labs.projectconf.ui.eventsItem.EventsItemAdapter
import io.rot.labs.projectconf.ui.eventsList.EventsListViewModel
import io.rot.labs.projectconf.ui.main.MainSharedViewModel
import io.rot.labs.projectconf.ui.main.MainViewModel
import io.rot.labs.projectconf.ui.search.SearchAdapter
import io.rot.labs.projectconf.ui.search.SearchViewModel
import io.rot.labs.projectconf.ui.settings.SettingsViewModel
import io.rot.labs.projectconf.utils.ViewModelProviderFactory
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
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
    fun provideSearchAdapter(): SearchAdapter = SearchAdapter(activity.lifecycle, ArrayList())

    @Provides
    fun provideArchiveAdapter(): ArchiveAdapter = ArchiveAdapter(activity.lifecycle, ArrayList())

    @Provides
    fun provideAllTechAdapter(): AllTechAdapter = AllTechAdapter(activity.lifecycle, ArrayList())

    @Provides
    fun provideMainViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkDBHelper: NetworkDBHelper
    ): MainViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(schedulerProvider, compositeDisposable, networkDBHelper)
        }).get(MainViewModel::class.java)
    }

    @Provides
    fun provideArchiveViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkDBHelper: NetworkDBHelper
    ): ArchiveViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(ArchiveViewModel::class) {
            ArchiveViewModel(schedulerProvider, compositeDisposable, networkDBHelper)
        }).get(ArchiveViewModel::class.java)
    }

    @Provides
    fun provideSettingsViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkDBHelper: NetworkDBHelper
    ): SettingsViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(SettingsViewModel::class) {
            SettingsViewModel(schedulerProvider, compositeDisposable, networkDBHelper)
        }).get(SettingsViewModel::class.java)
    }

    @Provides
    fun provideSearchViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkDBHelper: NetworkDBHelper,
        eventsRepository: EventsRepository
    ): SearchViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(SearchViewModel::class) {
            SearchViewModel(
                schedulerProvider,
                compositeDisposable,
                networkDBHelper,
                eventsRepository
            )
        }).get(SearchViewModel::class.java)
    }

    @Provides
    fun provideEventsListViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkDBHelper: NetworkDBHelper,
        eventsRepository: EventsRepository
    ): EventsListViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(EventsListViewModel::class) {
            EventsListViewModel(
                schedulerProvider,
                compositeDisposable,
                networkDBHelper,
                eventsRepository
            )
        }).get(EventsListViewModel::class.java)
    }

    @Provides
    fun provideEventDetailsViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkDBHelper: NetworkDBHelper,
        eventsRepository: EventsRepository
    ): EventDetailsViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(EventDetailsViewModel::class) {
            EventDetailsViewModel(
                schedulerProvider,
                compositeDisposable,
                networkDBHelper,
                eventsRepository
            )
        }).get(EventDetailsViewModel::class.java)
    }

    @Provides
    fun provideMainSharedViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkDBHelper: NetworkDBHelper
    ): MainSharedViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(MainSharedViewModel::class) {
            MainSharedViewModel(schedulerProvider, compositeDisposable, networkDBHelper)
        }).get(MainSharedViewModel::class.java)
    }

    @Provides
    fun provideAllTechViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkDBHelper: NetworkDBHelper
    ): AllTechViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(AllTechViewModel::class) {
            AllTechViewModel(schedulerProvider, compositeDisposable, networkDBHelper)
        }).get(AllTechViewModel::class.java)
    }
}
