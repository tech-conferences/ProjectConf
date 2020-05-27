package io.rot.labs.projectconf.di.component

import androidx.room.Database
import dagger.Component
import io.rot.labs.projectconf.ConfApplication
import io.rot.labs.projectconf.data.local.db.DatabaseService
import io.rot.labs.projectconf.data.remote.NetworkService
import io.rot.labs.projectconf.di.module.ApplicationModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: ConfApplication)

    fun getNetworkService(): NetworkService

    fun getDatabaseService(): DatabaseService

}