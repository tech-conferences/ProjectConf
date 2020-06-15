package io.rot.labs.projectconf.di.module

import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.ConfApplication
import io.rot.labs.projectconf.data.local.db.ConfDatabase
import io.rot.labs.projectconf.data.local.db.DatabaseService
import io.rot.labs.projectconf.data.local.db.FakeDatabaseService
import io.rot.labs.projectconf.data.remote.FakeNetworkService
import io.rot.labs.projectconf.data.remote.NetworkService
import io.rot.labs.projectconf.utils.display.ScreenResourcesHelper
import io.rot.labs.projectconf.utils.display.ScreenUtils
import io.rot.labs.projectconf.utils.network.FakeNetworkDBHelper
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.RxSchedulerProvider
import io.rot.labs.projectconf.utils.rx.SchedulerProvider
import javax.inject.Singleton

@Module
class ApplicationTestModule(private val application: ConfApplication) {
    @Provides
    @Singleton
    fun provideApplication() = application

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService = FakeNetworkService()

    @Provides
    @Singleton
    fun provideConfDatabase(): ConfDatabase =
        Room.databaseBuilder(application, ConfDatabase::class.java, "events.db")
            .build()

    @Provides
    @Singleton
    fun provideDatabaseService(): DatabaseService = FakeDatabaseService(provideConfDatabase())

    @Provides
    @Singleton
    fun provideNetworkHelper(): NetworkDBHelper = FakeNetworkDBHelper(application)

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    fun provideScreenResourceHelper(): ScreenResourcesHelper = ScreenUtils
}
