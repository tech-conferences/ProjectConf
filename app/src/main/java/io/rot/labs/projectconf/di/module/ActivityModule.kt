package io.rot.labs.projectconf.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.ui.archive.ArchiveViewModel
import io.rot.labs.projectconf.ui.base.BaseActivity
import io.rot.labs.projectconf.ui.main.MainViewModel
import io.rot.labs.projectconf.ui.search.SearchViewModel
import io.rot.labs.projectconf.ui.settings.SettingsViewModel
import io.rot.labs.projectconf.utils.ViewModelProviderFactory
import io.rot.labs.projectconf.utils.network.NetworkHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

@Module
class ActivityModule(private val activity: BaseActivity<*>) {

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
}
