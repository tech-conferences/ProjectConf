package io.rot.labs.projectconf.ui.eventsList

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.TestComponentRule
import io.rot.labs.projectconf.data.local.db.FakeDatabaseService
import io.rot.labs.projectconf.utils.common.Topics
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EventsListActivityTest {

    val component = TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    val intentsRule = IntentsTestRule(EventsListActivity::class.java, false, false)

    @get:Rule
    val rule = RuleChain.outerRule(component).around(intentsRule)

    lateinit var fakeDbService: FakeDatabaseService

    @Before
    fun setup() {
        fakeDbService = component.testComponent!!.getDatabaseService() as FakeDatabaseService
    }

    @Test
    fun eventsList_ConnectError_ShouldShowRetry() {

        fakeDbService.toThrowConnectException = true

        intentsRule.launchActivity(
            Intent(component.getContext(), EventsListActivity::class.java).apply {
                putExtra(EventsListActivity.TOPICS_LIST, arrayListOf(Topics.KOTLIN))
                putExtra(EventsListActivity.TOPIC_SUB, "${Topics.KOTLIN} devs")
                putExtra(EventsListActivity.TOPIC_TITLE, Topics.KOTLIN)
            }
        )

        onView(withId(R.id.tvGenericTitle)).check(matches(withText(Topics.KOTLIN)))

        onView(withId(R.id.layoutNoConnection)).check(matches(isDisplayed()))
    }

    @Test
    fun eventsList_TimeoutError_ShouldShowRetry() {

        fakeDbService.toThrowTimeOutException = true

        intentsRule.launchActivity(
            Intent(component.getContext(), EventsListActivity::class.java).apply {
                putExtra(EventsListActivity.TOPICS_LIST, arrayListOf(Topics.KOTLIN))
                putExtra(EventsListActivity.TOPIC_SUB, "${Topics.KOTLIN} devs")
                putExtra(EventsListActivity.TOPIC_TITLE, Topics.KOTLIN)
            }
        )

        onView(withId(R.id.tvGenericTitle)).check(matches(withText(Topics.KOTLIN)))

        onView(withId(R.id.layoutError)).check(matches(isDisplayed()))
    }
}
