package io.rot.labs.projectconf.ui.upcoming

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.TestComponentRule
import io.rot.labs.projectconf.utils.testHelper.ActionHelper.waitFor
import io.rot.labs.projectconf.utils.testHelper.RVMatcher.atPositionOnView
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UpComingFragmentTest {

    val component = TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    @get:Rule
    val rule = RuleChain.outerRule(component)

    @Test
    fun upcomingEventsAvailable_shouldDisplay() {
        launchFragmentInContainer<UpComingFragment>(Bundle(), R.style.Theme_ProjectConf)

        onView(withId(R.id.rvEvents)).check(matches(isDisplayed()))

        onView(withId(R.id.rvEvents)).check(
            matches(
                atPositionOnView(
                    1,
                    withText("Pragma Conf"),
                    R.id.tvConfName
                )
            )
        )
        onView(withId(R.id.rvEvents)).check(
            matches(
                atPositionOnView(
                    2,
                    withText("Kotliners Conf"),
                    R.id.tvConfName
                )
            )
        )
    }


    @Test
    fun bannersAvailable_shouldScroll() {
        launchFragmentInContainer<UpComingFragment>(Bundle(), R.style.Theme_ProjectConf)
        onView(withId(R.id.techBannerPager)).check(matches(isDisplayed()))

        onView(withId(R.id.tvCloudDevOps)).check(matches(isDisplayed()))

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.swipeLeft())

        onView(withId(R.id.tvMobileDev)).check(matches(isDisplayed()))

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.swipeLeft())

        onView(withId(R.id.tvDesign)).check(matches(isDisplayed()))

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.swipeLeft())

        onView(withId(R.id.tvJvmUniverse)).check(matches(isDisplayed()))

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.swipeLeft())

        onView(withId(R.id.tvJSLand)).check(matches(isDisplayed()))
    }
}