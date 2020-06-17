package io.rot.labs.projectconf.ui.allTech

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.TestComponentRule
import io.rot.labs.projectconf.utils.common.Topics
import io.rot.labs.projectconf.utils.testHelper.RVMatcher.atPositionOnView
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AllTechActivityTest {

    val component = TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    val intentsTestRule = IntentsTestRule(AllTechActivity::class.java, false, false)

    @get:Rule
    val rule = RuleChain.outerRule(component).around(intentsTestRule)

    @Test
    fun allTechList_available_shouldDisplay() {

        intentsTestRule.launchActivity(
            Intent(
                component.getContext(),
                AllTechActivity::class.java
            ).apply {
                putExtra(AllTechActivity.IS_ARCHIVE, true)
                putExtra(AllTechActivity.YEARS, arrayListOf(2019))
            })

        onView(withId(R.id.rvAllTech)).check(
            matches(
                atPositionOnView(
                    0,
                    withText(Topics.ANDROID),
                    R.id.tvAllTech
                )
            )
        )
    }
}
