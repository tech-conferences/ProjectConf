package io.rot.labs.projectconf.ui.alerts.userTopicChooser

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.TestComponentRule
import io.rot.labs.projectconf.ui.alerts.userTopicsChooser.AlertTopicChooserDialogFragment
import io.rot.labs.projectconf.utils.testHelper.RVMatcher.atPositionOnView
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlertsTopicChooserDialogFragmentTest {

    val component = TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    @get:Rule
    val rule = RuleChain.outerRule(component)

    @Test
    fun topics_available_should_display() {
        launchFragmentInContainer<AlertTopicChooserDialogFragment>(
            Bundle(),
            R.style.Theme_ProjectConf
        )

        onView(withId(R.id.rvTopicsChoose)).check(matches(isDisplayed()))

        onView(withId(R.id.btnDone)).check(matches(isDisplayed()))

        onView(withId(R.id.rvTopicsChoose)).check(
            matches(
                atPositionOnView(
                    0,
                    withText("android"),
                    R.id.topicCheckBox
                )
            )
        )
    }
}
