package io.rot.labs.projectconf.ui.search

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
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
class SearchActivityTest {

    val component = TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    val intentsTestRule = IntentsTestRule(SearchActivity::class.java, false, false)

    @get:Rule
    val rule = RuleChain.outerRule(component).around(intentsTestRule)

    @Test
    fun onSearchResultAvailable_should_display() {
        val yearsList = TimeDateUtils.getConfYearsList()
        val recentYears = arrayListOf(yearsList.last() - 1, yearsList.last())

        intentsTestRule.launchActivity(
            Intent(
                component.getContext(),
                SearchActivity::class.java
            ).apply {
                putExtra(SearchActivity.YEAR_LIST, recentYears)
            })

        onView(withId(R.id.etSearch)).check(matches(isDisplayed()))

        onView(withId(R.id.ivClearSearch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))

        onView(withId(R.id.etSearch)).perform(
            typeText("Pragma"),
            pressImeActionButton()
        )

        onView(withId(R.id.rvSearchResults)).check(
            matches(
                atPositionOnView(
                    0,
                    withText("PragmaConf"),
                    R.id.tvConfNameSearch
                )
            )
        )
    }

    @Test
    fun onSearchResultNotAvailable_should_display() {
        val yearsList = TimeDateUtils.getConfYearsList()
        val recentYears = arrayListOf(yearsList.last() - 1, yearsList.last())

        intentsTestRule.launchActivity(
            Intent(
                component.getContext(),
                SearchActivity::class.java
            ).apply {
                putExtra(SearchActivity.YEAR_LIST, recentYears)
            })

        onView(withId(R.id.etSearch)).check(matches(isDisplayed()))

        onView(withId(R.id.ivClearSearch)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))

        onView(withId(R.id.etSearch)).perform(
            typeText("iota"),
            pressImeActionButton()
        )

        onView(withId(R.id.layoutListIsEmptySearch)).check(matches(isDisplayed()))
    }
}
