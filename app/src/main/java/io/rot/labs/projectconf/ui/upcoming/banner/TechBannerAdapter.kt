/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.upcoming.banner

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TechBannerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    companion object {
        const val NUM_BANNERS = 5
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment =
        BannerFragment.newInstance(position % NUM_BANNERS)
}
