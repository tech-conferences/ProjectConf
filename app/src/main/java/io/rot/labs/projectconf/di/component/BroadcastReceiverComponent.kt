package io.rot.labs.projectconf.di.component

import dagger.Component
import io.rot.labs.projectconf.background.reminders.AlarmBroadcastReceiver
import io.rot.labs.projectconf.di.BroadcastScope

@BroadcastScope
@Component(dependencies = [ApplicationComponent::class])
interface BroadcastReceiverComponent {
    fun inject(broadcastReceiver: AlarmBroadcastReceiver)
}
