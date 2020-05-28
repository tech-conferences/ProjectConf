package io.rot.labs.projectconf.di.module

import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import io.rot.labs.projectconf.BuildConfig
import io.rot.labs.projectconf.ConfApplication
import io.rot.labs.projectconf.data.local.db.DatabaseService
import io.rot.labs.projectconf.data.remote.ConfApi
import io.rot.labs.projectconf.utils.network.NetworkHelper
import io.rot.labs.projectconf.utils.network.NetworkHelperImpl
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
    fun provideDatabaseService(): DatabaseService =
        Room.databaseBuilder(confApplication, DatabaseService::class.java, "events.db")
            .build()

    @Provides
    @Singleton
    fun provideNetworkHelper(): NetworkHelper = NetworkHelperImpl(confApplication)

}