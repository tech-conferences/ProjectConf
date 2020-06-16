package io.rot.labs.projectconf.ui.allTech

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import io.rot.labs.projectconf.ui.base.BaseAdapter

class AllTechAdapter(parentLifecycle: Lifecycle, dataList: ArrayList<Pair<String, Int?>>) :
    BaseAdapter<Pair<String, Int?>, AllTechItemViewHolder>(parentLifecycle, dataList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AllTechItemViewHolder(parent)
}
