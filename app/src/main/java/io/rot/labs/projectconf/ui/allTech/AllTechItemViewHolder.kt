package io.rot.labs.projectconf.ui.allTech

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.ViewHolderComponent
import io.rot.labs.projectconf.ui.base.BaseItemViewHolder
import io.rot.labs.projectconf.ui.eventsItem.EventsItemHelper
import io.rot.labs.projectconf.utils.display.ImageUtils
import kotlinx.android.synthetic.main.item_all_tech.view.allTechCard
import kotlinx.android.synthetic.main.item_all_tech.view.ivAllTech
import kotlinx.android.synthetic.main.item_all_tech.view.tvAllTech

class AllTechItemViewHolder(parent: ViewGroup) :
    BaseItemViewHolder<Pair<String, Int?>, AllTechItemViewModel>(R.layout.item_all_tech, parent) {

    private var tech: String? = null
    private var year: Int? = null

    override fun injectDependencies(buildComponent: ViewHolderComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(view: View) {
        itemView.allTechCard.setOnClickListener {
            tech?.let {
                EventsItemHelper.navigateToEventsListActivity(
                    itemView.context,
                    it,
                    "$it folks",
                    arrayListOf(it),
                    year != null,
                    year ?: -1
                )
            }
        }
    }

    override fun setUpObservables() {
        super.setUpObservables()
        viewModel.tech.observe(this, Observer {
            tech = it
            itemView.tvAllTech.text = it
        })

        viewModel.techResId.observe(this, Observer {
            ImageUtils.loadImageDrawable(itemView.context, it, itemView.ivAllTech)
        })

        viewModel.year.observe(this, Observer {
            year = it
        })
    }
}
