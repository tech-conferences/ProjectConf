/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.di.component

import dagger.Component
import io.rot.labs.projectconf.background.reminders.AlarmBroadcastReceiver
import io.rot.labs.projectconf.di.BroadcastScope

@BroadcastScope
@Component(dependencies = [ApplicationComponent::class])
interface BroadcastReceiverComponent {
    fun inject(broadcastReceiver: AlarmBroadcastReceiver)
}
