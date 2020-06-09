package io.rot.labs.projectconf.di.module

import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.BuildConfig
import io.rot.labs.projectconf.ConfApplication
import io.rot.labs.projectconf.data.local.db.ConfDatabase
import io.rot.labs.projectconf.data.local.db.DatabaseService
import io.rot.labs.projectconf.data.remote.ConfApi
import io.rot.labs.projectconf.utils.display.ScreenResourcesHelper
import io.rot.labs.projectconf.utils.display.ScreenUtils
import io.rot.labs.projectconf.utils.network.NetworkHelper
import io.rot.labs.projectconf.utils.network.NetworkHelperImpl
import io.rot.labs.projectconf.utils.rx.RxSchedulerProvider
import io.rot.labs.projectconf.utils.rx.SchedulerProvider
import javax.inject.Singleton

@Module
class ApplicationModule(private val confApplication: ConfApplication) {

    @Provides
    @Singleton
    fun provideApplication() = confApplication

    @Provides
    @Singleton
    fun provideNetworkService() = ConfApi.create(
        BuildConfig.BASE_URL,
        confApplication.cacheDir,
        10 * 1024 * 1024 // 10 mb
    )

    @Provides
    @Singleton
    fun provideConfDatabase(): ConfDatabase =
        Room.databaseBuilder(confApplication, ConfDatabase::class.java, "events.db")
            .build()

    @Provides
    @Singleton
    fun provideDataBaseService(): DatabaseService = DatabaseService(provideConfDatabase())

    @Provides
    @Singleton
    fun provideNetworkHelper(): NetworkHelper = NetworkHelperImpl(confApplication)

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    fun provideScreenResourceHelper(): ScreenResourcesHelper = ScreenUtils
}
