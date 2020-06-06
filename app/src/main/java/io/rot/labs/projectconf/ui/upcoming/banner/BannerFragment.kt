package io.rot.labs.projectconf.ui.upcoming.banner

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.rot.labs.projectconf.R

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
                0 -> {

                }
                1 -> {

                }
                2 -> {

                }
                3 -> {

                }
            }
        }


    }
}