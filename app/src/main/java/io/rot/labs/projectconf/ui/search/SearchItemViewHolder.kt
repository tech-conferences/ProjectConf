package io.rot.labs.projectconf.ui.search

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.di.component.ViewHolderComponent
import io.rot.labs.projectconf.ui.base.BaseItemViewHolder
import io.rot.labs.projectconf.ui.eventDetails.EventDetailsActivity
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import java.util.Date
import kotlinx.android.synthetic.main.item_search_result.view.searchResultContainer
import kotlinx.android.synthetic.main.item_search_result.view.tvConfNameSearch
import kotlinx.android.synthetic.main.item_search_result.view.tvEventDateSearch
import kotlinx.android.synthetic.main.item_search_result.view.tvEventPlaceSearch

class SearchItemViewHolder(parent: ViewGroup) :
    BaseItemViewHolder<EventEntity, SearchItemViewModel>(R.layout.item_search_result, parent) {

    private var eventName: String? = null
    private var startDate: Date? = null
    private var topic: String? = null

    override fun injectDependencies(buildComponent: ViewHolderComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(view: View) {
        itemView.searchResultContainer.setOnClickListener {
            itemView.context.startActivity(
                Intent(
                    itemView.context,
                    EventDetailsActivity::class.java
                ).apply {
                    putExtra(EventDetailsActivity.EVENT_NAME, eventName)
                    putExtra(EventDetailsActivity.EVENT_START_DATE, startDate!!.time)
                    putExtra(EventDetailsActivity.EVENT_TOPIC, topic)
                })
        }
    }

    override fun setUpObservables() {
        super.setUpObservables()

        viewModel.name.observe(this, Observer {
            eventName = it
            itemView.tvConfNameSearch.text = it
        })

        viewModel.place.observe(this, Observer {
            itemView.tvEventPlaceSearch.text = it
        })

        viewModel.startDate.observe(this, Observer {
            startDate = it
            itemView.tvEventDateSearch.text = TimeDateUtils.getFormattedDay(it)
        })

        viewModel.topic.observe(this, Observer {
            topic = it
        })
    }
}
