package io.rot.labs.projectconf.di.component

import dagger.Component
import io.rot.labs.projectconf.di.ViewModelScope
import io.rot.labs.projectconf.di.module.ViewHolderModule
import io.rot.labs.projectconf.ui.alerts.AlertsItemViewHolder
import io.rot.labs.projectconf.ui.alerts.userTopicsChooser.AlertTopicChooserItemViewHolder
import io.rot.labs.projectconf.ui.allTech.AllTechItemViewHolder
import io.rot.labs.projectconf.ui.archive.ArchiveItemViewHolder
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

    fun inject(viewHolder: ArchiveItemViewHolder)

    fun inject(viewHolder: AllTechItemViewHolder)

    fun inject(viewHolder: AlertsItemViewHolder)

    fun inject(viewHolder: AlertTopicChooserItemViewHolder)
}
