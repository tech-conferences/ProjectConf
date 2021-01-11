/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.archive

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.TestComponentRule
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.testHelper.RVMatcher.atPositionOnView
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArchiveActivityTest {

    val component = TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    @get:Rule
    val rule = RuleChain.outerRule(component)

    @Test
    fun archiveYears_available_shouldDisplay() {
        launch(ArchiveActivity::class.java)

        val lastYear = TimeDateUtils.getConfYearsList().last() - 1
        onView(withId(R.id.rvArchiveYears)).check(
            matches(
                atPositionOnView(
                    0,
                    withText(lastYear.toString()),
                    R.id.tvArchiveYear
                )
            )
        )
    }
}
