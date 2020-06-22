package io.rot.labs.projectconf.ui.alerts.userTopicsChooser

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.data.local.prefs.UserTopicPreferences
import io.rot.labs.projectconf.di.component.FragmentComponent
import io.rot.labs.projectconf.ui.alerts.alertsView.AlertsViewModel
import io.rot.labs.projectconf.ui.base.BaseBottomSheetDialogFragment
import io.rot.labs.projectconf.utils.common.TopicsList
import io.rot.labs.projectconf.utils.display.ScreenResourcesHelper
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_alert_topic_chooser.btnDone
import kotlinx.android.synthetic.main.fragment_alert_topic_chooser.rvTopicsChoose

class AlertTopicChooserDialogFragment : BaseBottomSheetDialogFragment<AlertsViewModel>() {

    companion object {
        const val TAG = "AlertTopicChooserDialogFragment"
    }

    @Inject
    lateinit var gridLayoutManager: GridLayoutManager

    @Inject
    lateinit var alertTopicChooserAdapter: AlertTopicChooserAdapter

    @Inject
    lateinit var screenResourcesHelper: ScreenResourcesHelper

    @Inject
    lateinit var userTopicPreferences: UserTopicPreferences

    override fun provideLayoutResId(): Int = R.layout.fragment_alert_topic_chooser

    override fun injectDependencies(buildComponent: FragmentComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        rvTopicsChoose.apply {
            adapter = alertTopicChooserAdapter

            layoutManager = if (screenResourcesHelper.isPortrait()) {
                gridLayoutManager.apply { spanCount = 2 }
            } else {
                gridLayoutManager.apply { spanCount = 3 }
            }
        }

        btnDone.setOnClickListener {
            userTopicPreferences.edit(alertTopicChooserAdapter.getUserTopicList().toList())
            viewModel.userTopics.postValue(
                alertTopicChooserAdapter.getUserTopicList().sorted().toList()
            )
            dismiss()
        }
    }

    override fun setupObservables() {
        viewModel.userTopics.observe(this, Observer {
            val result = transformToCheckedList(it)
            alertTopicChooserAdapter.updateData(result)
        })
    }

    private fun transformToCheckedList(list: List<String>): List<Pair<String, Boolean>> {
        var result = mutableListOf<Pair<String, Boolean>>()

        if (list.isNotEmpty()) {
            TopicsList.allIn.forEach {
                if (it in list) {
                    result.add(it to true)
                } else {
                    result.add(it to false)
                }
            }
        } else {
            result = TopicsList.allIn.map {
                it to false
            }.toMutableList()
        }
        return result
    }
}
