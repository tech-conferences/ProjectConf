package io.rot.labs.projectconf.ui.upcoming.banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.ui.eventsItem.EventsItemHelper.navigateToEventsListActivity
import io.rot.labs.projectconf.utils.common.Topics
import io.rot.labs.projectconf.utils.display.ImageUtils.loadImageDrawable
import kotlinx.android.synthetic.main.item_banner_design.view.ivDesignCard
import kotlinx.android.synthetic.main.item_banner_devops.view.ivDevopsCard
import kotlinx.android.synthetic.main.item_banner_js_land.view.ivJavaScriptCard
import kotlinx.android.synthetic.main.item_banner_js_land.view.ivTypeScriptCard
import kotlinx.android.synthetic.main.item_banner_jvm.view.ivGroovyCard
import kotlinx.android.synthetic.main.item_banner_jvm.view.ivJavaCard
import kotlinx.android.synthetic.main.item_banner_jvm.view.ivKotlinCard
import kotlinx.android.synthetic.main.item_banner_jvm.view.ivScalaCard
import kotlinx.android.synthetic.main.item_banner_mobile_dev.view.ivAndroidCard
import kotlinx.android.synthetic.main.item_banner_mobile_dev.view.ivIosCard
import kotlinx.android.synthetic.main.item_banner_mobile_dev.view.ivKotlinCardMB

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
            0 -> {
                val view = inflater.inflate(R.layout.item_banner_devops, container, false)
                loadImageDrawable(requireContext(), R.drawable.devops_logo, view.ivDevopsCard)
                view
            }
            1 -> {
                val view = inflater.inflate(R.layout.item_banner_mobile_dev, container, false)
                loadImageDrawable(requireContext(), R.drawable.android_logo, view.ivAndroidCard)
                loadImageDrawable(requireContext(), R.drawable.kotlin_logo, view.ivKotlinCardMB)
                loadImageDrawable(requireContext(), R.drawable.ios_logo, view.ivIosCard)
                view
            }
            2 -> {
                val view = inflater.inflate(R.layout.item_banner_design, container, false)
                loadImageDrawable(requireContext(), R.drawable.ux_logo, view.ivDesignCard)
                view
            }
            3 -> {
                val view = inflater.inflate(R.layout.item_banner_jvm, container, false)
                loadImageDrawable(requireContext(), R.drawable.java_logo, view.ivJavaCard)
                loadImageDrawable(requireContext(), R.drawable.kotlin_logo, view.ivKotlinCard)
                loadImageDrawable(requireContext(), R.drawable.scala_logo, view.ivScalaCard)
                loadImageDrawable(requireContext(), R.drawable.groovy_logo, view.ivGroovyCard)
                view
            }
            4 -> {
                val view = inflater.inflate(R.layout.item_banner_js_land, container, false)
                loadImageDrawable(requireContext(), R.drawable.javascript_logo, view.ivJavaScriptCard)
                loadImageDrawable(requireContext(), R.drawable.typescript_logo, view.ivTypeScriptCard)
                view
            }
            else -> super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnClickListener {
            when (arguments?.get(POSITION)) {
                0 -> navigateToEventsListActivity(
                    requireContext(),
                    getString(R.string.cloud_devops_label),
                    getString(R.string.devops_devs),
                    arrayListOf(Topics.DEVOPS)
                )
                1 -> navigateToEventsListActivity(
                    requireContext(),
                    getString(R.string.mobile_dev_label),
                    getString(R.string.mobile_devs),
                    arrayListOf(Topics.ANDROID, Topics.IOS, Topics.KOTLIN)
                )
                2 -> navigateToEventsListActivity(
                    requireContext(),
                    getString(R.string.design_label),
                    getString(R.string.design_folks),
                    arrayListOf(Topics.UX)
                )
                3 -> navigateToEventsListActivity(
                    requireContext(),
                    getString(R.string.jvm_universe_label),
                    getString(R.string.jvm_folks),
                    arrayListOf(Topics.JAVA, Topics.SCALA, Topics.KOTLIN, Topics.GROOVY)
                )
                4 -> navigateToEventsListActivity(
                    requireContext(),
                    getString(R.string.js_land_label),
                    getString(R.string.js_devs),
                    arrayListOf(Topics.TYPESCRIPT, Topics.JAVASCRIPT)
                )
            }
        }
    }
}
