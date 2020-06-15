package io.rot.labs.projectconf.di.component

import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.ConfApplication
import io.rot.labs.projectconf.data.local.db.DatabaseService
import io.rot.labs.projectconf.data.remote.NetworkService
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.di.module.ApplicationModule
import io.rot.labs.projectconf.utils.display.ScreenResourcesHelper
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: ConfApplication)

    fun getNetworkService(): NetworkService

    fun getDatabaseService(): DatabaseService

    fun getNetworkHelper(): NetworkDBHelper

    fun getCompositeDisposable(): CompositeDisposable

    fun getSchedulerProvider(): SchedulerProvider

    fun getEventRepository(): EventsRepository

    fun getScreenResourceHelper(): ScreenResourcesHelper
}
