package io.rot.labs.projectconf.di.component

import dagger.Component
import io.rot.labs.projectconf.di.FragmentScope
import io.rot.labs.projectconf.di.module.FragmentModule
import io.rot.labs.projectconf.ui.alerts.AlertsFragment
import io.rot.labs.projectconf.ui.bookmarks.BookmarksFragment
import io.rot.labs.projectconf.ui.twitter.TwitterFragment
import io.rot.labs.projectconf.ui.upcoming.UpComingFragment


@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {

    fun inject(fragment: UpComingFragment)

    fun inject(fragment: TwitterFragment)

    fun inject(fragment: BookmarksFragment)

    fun inject(fragment: AlertsFragment)
}