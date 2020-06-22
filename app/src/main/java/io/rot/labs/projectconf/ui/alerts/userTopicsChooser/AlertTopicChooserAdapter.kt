package io.rot.labs.projectconf.ui.alerts.userTopicsChooser

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import io.rot.labs.projectconf.ui.base.BaseAdapter
import kotlinx.android.synthetic.main.item_alert_topic_choose.view.topicCheckBox

class AlertTopicChooserAdapter(
    parentLifecycle: Lifecycle,
    private val dataList: ArrayList<Pair<String, Boolean>>,
    private val userTopicList: MutableSet<String>
) : BaseAdapter<Pair<String, Boolean>, AlertTopicChooserItemViewHolder>(parentLifecycle, dataList) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = AlertTopicChooserItemViewHolder(parent, userTopicList)

    fun getUserTopicList() = userTopicList

    override fun onBindViewHolder(holder: AlertTopicChooserItemViewHolder, position: Int) {
        holder.itemView.topicCheckBox.setOnCheckedChangeListener(null)
        holder.itemView.topicCheckBox.setOnCheckedChangeListener { _, isChecked ->
            dataList[position] = dataList[position].first to isChecked
            holder.bind(dataList[position])
        }
        holder.bind(dataList[position])
    }
}
