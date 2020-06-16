package io.rot.labs.projectconf.ui.archive

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import io.rot.labs.projectconf.ui.base.BaseAdapter

class ArchiveAdapter(parentLifecycle: Lifecycle, dataList: ArrayList<Int>) :
    BaseAdapter<Int, ArchiveItemViewHolder>(parentLifecycle, dataList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArchiveItemViewHolder(parent)
}
