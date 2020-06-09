package io.rot.labs.projectconf

import android.app.Application
import io.rot.labs.projectconf.di.component.ApplicationComponent
import io.rot.labs.projectconf.di.component.DaggerApplicationComponent
import io.rot.labs.projectconf.di.module.ApplicationModule

class ConfApplication : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        appComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()

        appComponent.inject(this)
    }

    fun setComponent(appComponent: ApplicationComponent) {
        this.appComponent = appComponent
    }
}
