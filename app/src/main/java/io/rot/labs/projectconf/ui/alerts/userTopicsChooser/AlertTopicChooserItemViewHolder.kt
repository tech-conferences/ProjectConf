package io.rot.labs.projectconf.ui.alerts.userTopicsChooser

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.ViewHolderComponent
import io.rot.labs.projectconf.ui.base.BaseItemViewHolder
import io.rot.labs.projectconf.utils.display.ImageUtils
import kotlinx.android.synthetic.main.item_alert_topic_choose.view.ivAlertTopicChoose
import kotlinx.android.synthetic.main.item_alert_topic_choose.view.topicCheckBox

class AlertTopicChooserItemViewHolder(parent: ViewGroup, val userTopicList: MutableSet<String>) :
    BaseItemViewHolder<Pair<String, Boolean>, AlertTopicChooserItemViewModel>(
        R.layout.item_alert_topic_choose,
        parent
    ) {

    private var topic: String? = null

    override fun injectDependencies(buildComponent: ViewHolderComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(view: View) {
    }

    override fun setUpObservables() {
        super.setUpObservables()

        viewModel.name.observe(this, Observer {
            topic = it
            itemView.topicCheckBox.text = it
            ImageUtils.loadImageDrawable(
                itemView.context,
                ImageUtils.getTopicDrawableResId(it),
                itemView.ivAlertTopicChoose
            )
        })

        viewModel.isChosen.observe(this, Observer {
            topic?.let { topic ->
                if (it) {
                    Log.d("PUI", "topic $topic, isChosen $it")
                    if (!itemView.topicCheckBox.isChecked) {
                        itemView.topicCheckBox.isChecked = true
                    }
                    userTopicList.add(topic)
                } else {
                    if (itemView.topicCheckBox.isChecked) {
                        itemView.topicCheckBox.isChecked = false
                    }
                    userTopicList.remove(topic)
                }
            }
        })
    }
}
