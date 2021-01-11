/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.di.component

import dagger.Component
import io.rot.labs.projectconf.di.FragmentScope
import io.rot.labs.projectconf.di.module.FragmentModule
import io.rot.labs.projectconf.ui.alerts.alertsView.AlertsFragment
import io.rot.labs.projectconf.ui.alerts.userTopicsChooser.AlertTopicChooserDialogFragment
import io.rot.labs.projectconf.ui.bookmarks.BookmarksFragment
import io.rot.labs.projectconf.ui.changeTheme.ChangeThemeDialogFragment
import io.rot.labs.projectconf.ui.eventDetails.eventReminder.EventReminderDialogFragment
import io.rot.labs.projectconf.ui.upcoming.UpComingFragment

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {

    fun inject(fragment: UpComingFragment)

    fun inject(fragment: BookmarksFragment)

    fun inject(fragment: AlertsFragment)

    fun inject(fragment: AlertTopicChooserDialogFragment)

    fun inject(fragment: EventReminderDialogFragment)

    fun inject(fragment: ChangeThemeDialogFragment)
}
