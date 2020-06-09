package io.rot.labs.projectconf.di.component

import dagger.Component
import io.rot.labs.projectconf.di.ActivityScope
import io.rot.labs.projectconf.di.module.ActivityModule
import io.rot.labs.projectconf.ui.archive.ArchiveActivity
import io.rot.labs.projectconf.ui.main.MainActivity
import io.rot.labs.projectconf.ui.search.SearchActivity
import io.rot.labs.projectconf.ui.settings.SettingsActivity

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: ArchiveActivity)

    fun inject(activity: SettingsActivity)

    fun inject(activity: SearchActivity)
}
