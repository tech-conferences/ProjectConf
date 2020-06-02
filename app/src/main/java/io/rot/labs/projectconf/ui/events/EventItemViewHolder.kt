package io.rot.labs.projectconf.ui.events

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.data.model.Event
import io.rot.labs.projectconf.di.component.ViewHolderComponent
import io.rot.labs.projectconf.ui.base.BaseItemViewHolder

class EventItemViewHolder(private val parent: ViewGroup) :
    BaseItemViewHolder<Event, EventItemViewModel>(R.layout.item_conf, parent) {
    override fun injectDependencies(buildComponent: ViewHolderComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(view: View) {

    }


}