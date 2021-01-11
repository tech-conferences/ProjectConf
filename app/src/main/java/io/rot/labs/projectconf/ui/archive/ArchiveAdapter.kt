/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.archive

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import io.rot.labs.projectconf.ui.base.BaseAdapter

class ArchiveAdapter(parentLifecycle: Lifecycle, dataList: ArrayList<Int>) :
    BaseAdapter<Int, ArchiveItemViewHolder>(parentLifecycle, dataList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArchiveItemViewHolder(parent)
}
