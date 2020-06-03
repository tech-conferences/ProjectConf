package io.rot.labs.projectconf.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.ui.alerts.AlertsViewModel
import io.rot.labs.projectconf.ui.base.BaseFragment
import io.rot.labs.projectconf.ui.bookmarks.BookmarksViewModel
import io.rot.labs.projectconf.ui.twitter.TwitterViewModel
import io.rot.labs.projectconf.ui.upcoming.UpComingViewModel
import io.rot.labs.projectconf.utils.ViewModelProviderFactory
import io.rot.labs.projectconf.utils.network.NetworkHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @Provides
    fun provideUpComingViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        eventsRepository: EventsRepository
    ): UpComingViewModel {
        return ViewModelProvider(fragment, ViewModelProviderFactory(UpComingViewModel::class) {
            UpComingViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper,
                eventsRepository
            )
        }).get(UpComingViewModel::class.java)
    }


    @Provides
    fun provideTwitterViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): TwitterViewModel {
        return ViewModelProvider(fragment, ViewModelProviderFactory(TwitterViewModel::class) {
            TwitterViewModel(schedulerProvider, compositeDisposable, networkHelper)
        }).get(TwitterViewModel::class.java)
    }

    @Provides
    fun provideAlertsViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): AlertsViewModel {
        return ViewModelProvider(fragment, ViewModelProviderFactory(AlertsViewModel::class) {
            AlertsViewModel(schedulerProvider, compositeDisposable, networkHelper)
        }).get(AlertsViewModel::class.java)
    }

    @Provides
    fun provideBookmarksViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): BookmarksViewModel {
        return ViewModelProvider(fragment, ViewModelProviderFactory(BookmarksViewModel::class) {
            BookmarksViewModel(schedulerProvider, compositeDisposable, networkHelper)
        }).get(BookmarksViewModel::class.java)
    }
}