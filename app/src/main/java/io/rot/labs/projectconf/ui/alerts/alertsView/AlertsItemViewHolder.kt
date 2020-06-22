package io.rot.labs.projectconf.ui.alerts.alertsView

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.ViewHolderComponent
import io.rot.labs.projectconf.ui.base.BaseItemViewHolder
import io.rot.labs.projectconf.utils.display.ImageUtils
import kotlinx.android.synthetic.main.item_all_tech.view.ivAllTech
import kotlinx.android.synthetic.main.item_all_tech.view.tvAllTech

class AlertsItemViewHolder(parent: ViewGroup) : BaseItemViewHolder<String, AlertsItemViewModel>(
    R.layout.item_all_tech,
    parent
) {
    override fun injectDependencies(buildComponent: ViewHolderComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(view: View) {}

    override fun setUpObservables() {
        super.setUpObservables()
        viewModel.data.observe(this, Observer {
            itemView.tvAllTech.text = it

            ImageUtils.loadImageDrawable(
                itemView.context,
                ImageUtils.getTopicDrawableResId(it),
                itemView.ivAllTech
            )
        })
    }
}
