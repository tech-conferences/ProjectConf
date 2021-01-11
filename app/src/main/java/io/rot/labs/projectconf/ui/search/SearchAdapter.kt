/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.search

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.ui.base.BaseAdapter

class SearchAdapter(parentLifeCycle: Lifecycle, list: ArrayList<EventEntity>) :
    BaseAdapter<EventEntity, SearchItemViewHolder>(parentLifeCycle, list) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchItemViewHolder(parent)
}
