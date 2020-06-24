package io.rot.labs.projectconf.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.work.Configuration
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.BuildConfig
import io.rot.labs.projectconf.ConfApplication
import io.rot.labs.projectconf.data.local.db.ConfDatabase
import io.rot.labs.projectconf.data.local.prefs.ThemePreferences
import io.rot.labs.projectconf.data.remote.ConfApi
import io.rot.labs.projectconf.data.work.ManagerWorkerFactory
import io.rot.labs.projectconf.utils.display.ScreenResourcesHelper
import io.rot.labs.projectconf.utils.display.ScreenUtils
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.network.NetworkDBHelperImpl
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
    fun provideNetworkHelper(): NetworkDBHelper = NetworkDBHelperImpl(confApplication)

    @Provides
    @Singleton
    fun provideWorkManagerConfiguration(managerWorkerFactory: ManagerWorkerFactory): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(managerWorkerFactory)
            .build()

    @Provides
    @Singleton
    fun providesSharedPreferences(): SharedPreferences = confApplication.getSharedPreferences(
        "UserPrefs",
        Context.MODE_PRIVATE
    )

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Singleton
    @Provides
    fun provideScreenResourceHelper(themePreferences: ThemePreferences): ScreenResourcesHelper =
        ScreenUtils(themePreferences)
}
