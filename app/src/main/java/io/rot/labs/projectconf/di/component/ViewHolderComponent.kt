package io.rot.labs.projectconf.di.component

import dagger.Component
import io.rot.labs.projectconf.di.ViewModelScope
import io.rot.labs.projectconf.di.module.ViewHolderModule
import io.rot.labs.projectconf.ui.events.EventItemViewHolder

@ViewModelScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ViewHolderModule::class]
)
interface ViewHolderComponent {

    fun inject(viewHolder: EventItemViewHolder)

}