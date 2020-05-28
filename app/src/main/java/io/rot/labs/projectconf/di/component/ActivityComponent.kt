package io.rot.labs.projectconf.di.component

import dagger.Component
import io.rot.labs.projectconf.di.ActivityScope
import io.rot.labs.projectconf.di.module.ActivityModule
import io.rot.labs.projectconf.ui.main.MainActivity

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {

    fun inject(activity: MainActivity)
}