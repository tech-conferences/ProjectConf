package io.rot.labs.projectconf.ui.events

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.model.EventBase
import io.rot.labs.projectconf.ui.base.BaseAdapter

class EventAdapter(
    parentLifecycle: Lifecycle,
    private val dataList: ArrayList<EventBase>
) : BaseAdapter<EventBase, EventBaseViewHolder>(parentLifecycle, dataList) {

    companion object {
        const val DETAIL = 0
        const val HEADER = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventBaseViewHolder {
        return if (viewType == DETAIL) {
            EventBaseViewHolder(R.layout.item_event, parent)
        } else {
            EventBaseViewHolder(R.layout.item_event_header, parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = dataList[position]
        return if (item is EventEntity) {
            DETAIL
        } else {
            HEADER
        }
    }
}
