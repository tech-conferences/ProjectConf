/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.utils.testHelper

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

object RVMatcher {

    fun atPositionOnView(
        position: Int,
        itemMatcher: Matcher<View>,
        targetViewId: Int
    ): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description?) {
                description?.appendText("has view id $itemMatcher at position $position")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                println("recyclerview id  ${recyclerView.id}")
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                println("Viewholder $viewHolder")
                val targetView = viewHolder!!.itemView.findViewById<View>(targetViewId)
                println("view  $targetView")
                return itemMatcher.matches(targetView)
            }
        }
    }
}
