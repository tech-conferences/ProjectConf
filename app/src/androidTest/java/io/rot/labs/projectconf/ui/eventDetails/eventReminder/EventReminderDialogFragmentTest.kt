package io.rot.labs.projectconf.ui.eventDetails.eventReminder

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.TestComponentRule
import io.rot.labs.projectconf.utils.testHelper.AndroidTestHelper
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EventReminderDialogFragmentTest {

    val component = TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    @get:Rule
    val rule = RuleChain.outerRule(component)

    @Test
    fun cfpScheduledId_not_present_should_show_set_reminder_dialog() {
        launchFragmentInContainer<EventReminderDialogFragment>(Bundle().apply {
            putParcelable(
                EventReminderDialogFragment.EVENT_ENTITY,
                AndroidTestHelper.fakeEventEntityList.last()
            )
            putInt(EventReminderDialogFragment.CFP_SCHEDULED_ID, -1)
        }, R.style.Theme_ProjectConf)

        onView(withId(R.id.layoutReminderSet)).check(matches(isDisplayed()))

        onView(withId(R.id.btnReminderSet)).perform(ViewActions.click())
    }

    @Test
    fun cfpScheduledId_present_should_show_cancel_reminder_dialog() {
        launchFragmentInContainer<EventReminderDialogFragment>(Bundle().apply {
            putParcelable(
                EventReminderDialogFragment.EVENT_ENTITY,
                AndroidTestHelper.fakeEventEntityList[2]
            )
            putInt(EventReminderDialogFragment.CFP_SCHEDULED_ID, 23)
        }, R.style.Theme_ProjectConf)

        onView(withId(R.id.layoutReminderCancel)).check(matches(isDisplayed()))

        onView(withId(R.id.tvYes)).perform(ViewActions.click())
    }
}
