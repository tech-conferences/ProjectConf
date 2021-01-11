/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.alerts.alertsView

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.FragmentComponent
import io.rot.labs.projectconf.ui.alerts.userTopicsChooser.AlertTopicChooserDialogFragment
import io.rot.labs.projectconf.ui.base.BaseFragment
import io.rot.labs.projectconf.utils.display.ScreenResourcesHelper
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_alerts.fabEdit
import kotlinx.android.synthetic.main.fragment_alerts.listIsEmptyAlerts
import kotlinx.android.synthetic.main.fragment_alerts.rvTopicsAlerts
import kotlinx.android.synthetic.main.fragment_alerts.tvAlertInfo

class AlertsFragment : BaseFragment<AlertsViewModel>() {

    companion object {
        const val TAG = "AlertsFragment"

        fun newInstance(): AlertsFragment {
            val args = Bundle()
            val fragment =
                AlertsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var alertsAdapter: AlertsAdapter

    @Inject
    lateinit var gridLayoutManager: GridLayoutManager

    @Inject
    lateinit var screenResourcesHelper: ScreenResourcesHelper

    override fun injectDependencies(buildComponent: FragmentComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        fabEdit.setOnClickListener {
            showAlertTopicChooseBottomFragment()
        }

        rvTopicsAlerts.apply {
            adapter = alertsAdapter
            layoutManager = if (screenResourcesHelper.isPortrait()) {
                gridLayoutManager.apply { spanCount = 2 }
            } else {
                gridLayoutManager.apply { spanCount = 3 }
            }
        }
    }

    fun showAlertTopicChooseBottomFragment() {
        AlertTopicChooserDialogFragment().show(
            requireActivity().supportFragmentManager,
            AlertTopicChooserDialogFragment.TAG
        )
    }

    override fun provideLayoutId(): Int = R.layout.fragment_alerts

    override fun setupObservables() {
        super.setupObservables()
        viewModel.userTopics.observe(this, Observer {
            alertsAdapter.updateData(it)
            if (it.isEmpty()) {
                listIsEmptyAlerts.isVisible = true
                rvTopicsAlerts.isVisible = false
                tvAlertInfo.isVisible = false
            } else {
                listIsEmptyAlerts.isVisible = false
                rvTopicsAlerts.isVisible = true
                tvAlertInfo.isVisible = true
            }
        })
    }
}
