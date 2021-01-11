/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.alerts.alertsNotification

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.TestComponentRule
import io.rot.labs.projectconf.utils.testHelper.AndroidTestHelper
import io.rot.labs.projectconf.utils.testHelper.RVMatcher.atPositionOnView
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlertsNotificationActivityTest {

    val component = TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    val intentsTestRule = IntentsTestRule(AlertsNotificationActivity::class.java, false, false)

    @get:Rule
    val rule = RuleChain.outerRule(component).around(intentsTestRule)

    @Test
    fun alerts_notification_available_should_display() {
        intentsTestRule.launchActivity(
            Intent(
                component.getContext(),
                AlertsNotificationActivity::class.java
            ).apply {
                putParcelableArrayListExtra(
                    AlertsNotificationActivity.ALERTS_LIST, arrayListOf(
                        AndroidTestHelper.fakeEventEntityList[1]
                    )
                )
            }
        )

        onView(withId(R.id.rvAlertEvents)).check(matches(isDisplayed()))

        onView(withId(R.id.rvAlertEvents)).check(
            matches(
                atPositionOnView(
                    1,
                    withText("KotlinersConf"),
                    R.id.tvConfName
                )
            )
        )
    }
}
