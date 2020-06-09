package io.rot.labs.projectconf.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.ui.alerts.AlertsViewModel
import io.rot.labs.projectconf.ui.base.BaseFragment
import io.rot.labs.projectconf.ui.bookmarks.BookmarksViewModel
import io.rot.labs.projectconf.ui.events.EventAdapter
import io.rot.labs.projectconf.ui.twitter.TwitterViewModel
import io.rot.labs.projectconf.ui.upcoming.UpComingViewModel
import io.rot.labs.projectconf.ui.upcoming.banner.BannerViewModel
import io.rot.labs.projectconf.ui.upcoming.banner.TechBannerAdapter
import io.rot.labs.projectconf.ui.upcoming.banner.ZoomOutPageTransformer
import io.rot.labs.projectconf.utils.ViewModelProviderFactory
import io.rot.labs.projectconf.utils.network.NetworkHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @Provides
    fun provideLinearLayoutManager() = LinearLayoutManager(fragment.context)

    @Provides
    fun provideGridLayoutManager(): GridLayoutManager = GridLayoutManager(fragment.context, 2)

    @Provides
    fun provideEventAdapter(): EventAdapter = EventAdapter(fragment.lifecycle, ArrayList())

    @Provides
    fun provideTechBannerAdapter(): TechBannerAdapter = TechBannerAdapter(fragment.activity!!)

    @Provides
    fun provideZoomOutTransformer(): ZoomOutPageTransformer = ZoomOutPageTransformer()

    @Provides
    fun provideBannerViewModel(): BannerViewModel =
        ViewModelProvider(fragment).get(BannerViewModel::class.java)

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
