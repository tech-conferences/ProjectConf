/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.eventDetails

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.TestComponentRule
import io.rot.labs.projectconf.utils.testHelper.AndroidTestHelper
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class EventDetailsActivityTest {

    val component = TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    val activityTestRule = ActivityTestRule(EventDetailsActivity::class.java, false, false)

    @get:Rule
    val rule = RuleChain.outerRule(component).around(activityTestRule)

    @Test
    fun basic_event_detail_available_should_display() {
        activityTestRule.launchActivity(
            Intent(component.getContext(), EventDetailsActivity::class.java).apply {
                putExtra(EventDetailsActivity.EVENT_NAME, "PragmaConf")
                putExtra(EventDetailsActivity.EVENT_START_DATE, AndroidTestHelper.eventDatePast)
                putExtra(EventDetailsActivity.EVENT_TOPIC, "ux")
            }
        )

        onView(withId(R.id.tvGenericTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvGenericTitle)).check(matches(withText("PragmaConf")))

        onView(withId(R.id.btnTwitter)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        onView(withId(R.id.tvCFPEndDate)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.btnCfpUrl)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        onView(withId(R.id.btnConfUrl)).check(matches(isDisplayed()))
    }

    @Test
    fun event_detail_with_twitter_available_should_display() {
        activityTestRule.launchActivity(
            Intent(component.getContext(), EventDetailsActivity::class.java).apply {
                putExtra(EventDetailsActivity.EVENT_NAME, "KotlinersConf")
                putExtra(EventDetailsActivity.EVENT_START_DATE, AndroidTestHelper.eventDatePast)
                putExtra(EventDetailsActivity.EVENT_TOPIC, "kotlin")
            }
        )

        onView(withId(R.id.tvGenericTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvGenericTitle)).check(matches(withText("KotlinersConf")))

        onView(withId(R.id.tvCFPEndDate)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.btnCfpUrl)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        onView(withId(R.id.btnTwitter)).check(matches(isDisplayed()))
    }

    @Test
    fun event_detail_all_available_should_display() {
        activityTestRule.launchActivity(
            Intent(component.getContext(), EventDetailsActivity::class.java).apply {
                putExtra(EventDetailsActivity.EVENT_NAME, "Kubecon")
                putExtra(EventDetailsActivity.EVENT_START_DATE, AndroidTestHelper.eventDatePast)
                putExtra(EventDetailsActivity.EVENT_TOPIC, "devops")
            }
        )

        onView(withId(R.id.tvGenericTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvGenericTitle)).check(matches(withText("Kubecon")))

        onView(withId(R.id.tvCFPEndDate)).check(matches(isDisplayed()))
    }

    @Test
    fun event_detail_no_cfpEndDate_available_should_display() {
        activityTestRule.launchActivity(
            Intent(component.getContext(), EventDetailsActivity::class.java).apply {
                putExtra(EventDetailsActivity.EVENT_NAME, "Cloudnative")
                putExtra(EventDetailsActivity.EVENT_START_DATE, AndroidTestHelper.eventDatePast)
                putExtra(EventDetailsActivity.EVENT_TOPIC, "devops")
            }
        )

        onView(withId(R.id.tvGenericTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvGenericTitle)).check(matches(withText("Cloudnative")))

        onView(withId(R.id.tvCFPEndDate)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.btnCfpUrl)).check(matches(isDisplayed()))
    }

    @Test
    fun event_detail_no_cfpUrl_available_should_display() {
        activityTestRule.launchActivity(
            Intent(component.getContext(), EventDetailsActivity::class.java).apply {
                putExtra(EventDetailsActivity.EVENT_NAME, "Rustlang")
                putExtra(EventDetailsActivity.EVENT_START_DATE, AndroidTestHelper.eventDatePast)
                putExtra(EventDetailsActivity.EVENT_TOPIC, "rust")
            }
        )

        onView(withId(R.id.tvGenericTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvGenericTitle)).check(matches(withText("Rustlang")))

        onView(withId(R.id.tvCFPEndDate)).check(matches(isDisplayed()))

        onView(withId(R.id.btnCfpUrl)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }
}
