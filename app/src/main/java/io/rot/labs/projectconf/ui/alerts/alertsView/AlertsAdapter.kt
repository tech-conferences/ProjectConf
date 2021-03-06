/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.alerts.alertsView

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import io.rot.labs.projectconf.ui.base.BaseAdapter

class AlertsAdapter(parentLifecycle: Lifecycle, dataList: ArrayList<String>) :
    BaseAdapter<String, AlertsItemViewHolder>(parentLifecycle, dataList) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AlertsItemViewHolder(parent)
}
