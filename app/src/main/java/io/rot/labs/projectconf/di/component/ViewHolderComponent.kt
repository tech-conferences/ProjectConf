package io.rot.labs.projectconf.di.component

import dagger.Component
import io.rot.labs.projectconf.di.ViewModelScope
import io.rot.labs.projectconf.di.module.ViewHolderModule
import io.rot.labs.projectconf.ui.eventsItem.EventsItemViewHolder
import io.rot.labs.projectconf.ui.search.SearchItemViewHolder

@ViewModelScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ViewHolderModule::class]
)
interface ViewHolderComponent {

    fun inject(viewHolder: EventsItemViewHolder)

    fun inject(viewHolder: SearchItemViewHolder)
}
