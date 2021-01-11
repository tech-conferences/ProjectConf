/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.eventsItem

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.model.EventItem
import io.rot.labs.projectconf.ui.base.BaseAdapter

class EventsItemAdapter(
    parentLifecycle: Lifecycle,
    private val dataList: ArrayList<EventItem>
) : BaseAdapter<EventItem, EventsItemViewHolder>(parentLifecycle, dataList) {

    companion object {
        const val DETAIL = 0
        const val HEADER = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsItemViewHolder {
        return if (viewType == DETAIL) {
            EventsItemViewHolder(R.layout.item_event, parent)
        } else {
            EventsItemViewHolder(R.layout.item_event_header, parent)
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
