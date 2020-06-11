package io.rot.labs.projectconf.ui.upcoming.banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.ui.eventsItem.EventsItemHelper.navigateToEventsListActivity
import io.rot.labs.projectconf.utils.common.Topics

class BannerFragment : Fragment() {

    companion object {

        private const val POSITION = "position"
        fun newInstance(position: Int): BannerFragment {
            val fragment = BannerFragment()
            val bundle = Bundle()
            bundle.putInt(POSITION, position)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return when (arguments?.get(POSITION)) {
            0 -> inflater.inflate(R.layout.item_banner_devops, container, false)
            1 -> inflater.inflate(R.layout.item_banner_mobile_dev, container, false)
            2 -> inflater.inflate(R.layout.item_banner_design, container, false)
            3 -> inflater.inflate(R.layout.item_banner_jvm, container, false)
            4 -> inflater.inflate(R.layout.item_banner_js_land, container, false)
            else -> super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnClickListener {
            when (arguments?.get(POSITION)) {
                0 -> navigateToEventsListActivity(
                    context!!,
                    getString(R.string.cloud_devops_label),
                    getString(R.string.devops_devs),
                    arrayListOf(Topics.DEVOPS)
                )
                1 -> navigateToEventsListActivity(
                    context!!,
                    getString(R.string.mobile_dev_label),
                    getString(R.string.mobile_devs),
                    arrayListOf(Topics.ANDROID, Topics.IOS, Topics.KOTLIN)
                )
                2 -> navigateToEventsListActivity(
                    context!!,
                    getString(R.string.design_label),
                    getString(R.string.design_folks),
                    arrayListOf(Topics.UX)
                )
                3 -> navigateToEventsListActivity(
                    context!!,
                    getString(R.string.jvm_universe_label),
                    getString(R.string.jvm_folks),
                    arrayListOf(Topics.JAVA, Topics.SCALA, Topics.KOTLIN, Topics.GROOVY)
                )
                4 -> navigateToEventsListActivity(
                    context!!,
                    getString(R.string.js_land_label),
                    getString(R.string.js_devs),
                    arrayListOf(Topics.TYPESCRIPT, Topics.JAVASCRIPT)
                )
            }
        }
    }
}
