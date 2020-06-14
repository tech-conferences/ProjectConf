package io.rot.labs.projectconf.ui.upcoming

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.TestComponentRule
import io.rot.labs.projectconf.data.local.db.FakeDatabaseService
import io.rot.labs.projectconf.utils.testHelper.RVMatcher.atPositionOnView
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UpComingFragmentTest {

    val component = TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    @get:Rule
    val rule = RuleChain.outerRule(component)

    lateinit var fakeDbService: FakeDatabaseService

    @Before
    fun setup() {
        fakeDbService = component.testComponent!!.getDatabaseService() as FakeDatabaseService
    }

    @Test
    fun upcomingEventsAvailable_shouldDisplay() {
        launchFragmentInContainer<UpComingFragment>(Bundle(), R.style.Theme_ProjectConf)

        onView(withId(R.id.rvEvents)).check(matches(isDisplayed()))

        onView(withId(R.id.rvEvents)).check(
            matches(
                atPositionOnView(
                    1,
                    withText("PragmaConf"),
                    R.id.tvConfName
                )
            )
        )
        onView(withId(R.id.rvEvents)).check(
            matches(
                atPositionOnView(
                    2,
                    withText("KotlinersConf"),
                    R.id.tvConfName
                )
            )
        )
    }

    @Test
    fun upcomingEventsConnectError_shouldShowRetry() {
        fakeDbService.toThrowConnectException = true

        launchFragmentInContainer<UpComingFragment>(Bundle(), R.style.Theme_ProjectConf)

        onView(withId(R.id.btnNoConnectionRetry)).check(matches(isDisplayed()))
    }

    @Test
    fun upcomingEventsTimeoutError_shouldShowRetry() {
        fakeDbService.toThrowTimeOutException = true

        launchFragmentInContainer<UpComingFragment>(Bundle(), R.style.Theme_ProjectConf)

        onView(withId(R.id.btnErrorRetry)).check(matches(isDisplayed()))
    }

    @Test
    fun upcomingEventsPython_shouldNavigate_To_Python_EventsList() {
        launchFragmentInContainer<UpComingFragment>(Bundle(), R.style.Theme_ProjectConf)

        onView(withId(R.id.ivPython)).perform(ViewActions.click())

        onView(withId(R.id.tvGenericTitle)).check(matches(withText("PYTHON")))
    }

    @Test
    fun upcomingEventsRust_shouldNavigate_To_Rust_EventsList() {
        launchFragmentInContainer<UpComingFragment>(Bundle(), R.style.Theme_ProjectConf)

        onView(withId(R.id.ivRust)).perform(ViewActions.click())

        onView(withId(R.id.tvGenericTitle)).check(matches(withText("RUST")))
    }

    @Test
    fun upcomingEventsGoLang_shouldNavigate_To_Golang_EventsList() {
        launchFragmentInContainer<UpComingFragment>(Bundle(), R.style.Theme_ProjectConf)

        onView(withId(R.id.ivGolang)).perform(ViewActions.click())

        onView(withId(R.id.tvGenericTitle)).check(matches(withText("GOLANG")))
    }

    @Test
    fun upcomingEventsGraphQl_shouldNavigate_To_GraphQl_EventsList() {
        launchFragmentInContainer<UpComingFragment>(Bundle(), R.style.Theme_ProjectConf)

        onView(withId(R.id.ivGraphql)).perform(ViewActions.click())

        onView(withId(R.id.tvGenericTitle)).check(matches(withText("GRAPHQL")))
    }

    @Test
    fun upcomingEventsCloudDevOps_shouldNavigate_To_CloudDevOps_EventsList() {
        launchFragmentInContainer<UpComingFragment>(Bundle(), R.style.Theme_ProjectConf)

        onView(withId(R.id.techBannerPager)).check(matches(isDisplayed()))

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.click())

        onView(withId(R.id.tvCloudDevOpsList)).check(matches(isDisplayed()))

        onView(withId(R.id.tvCloudDevOpsList)).check(matches(withText("Cloud and DevOps")))
    }

    @Test
    fun upcomingEventsMobileDev_shouldNavigate_To_MobileDev_EventsList() {
        launchFragmentInContainer<UpComingFragment>(Bundle(), R.style.Theme_ProjectConf)

        onView(withId(R.id.techBannerPager)).check(matches(isDisplayed()))

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.swipeLeft())

        Thread.sleep(1000)

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.click())

        onView(withId(R.id.tvMobileDevList)).check(matches(isDisplayed()))

        onView(withId(R.id.tvMobileDevList)).check(matches(withText("Mobile Development")))
    }

    @Test
    fun upcomingEventsDesign_shouldNavigate_To_Design_EventsList() {
        launchFragmentInContainer<UpComingFragment>(Bundle(), R.style.Theme_ProjectConf)

        onView(withId(R.id.techBannerPager)).check(matches(isDisplayed()))

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.swipeLeft())

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.swipeLeft())

        Thread.sleep(1000)

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.click())

        onView(withId(R.id.tvDesignList)).check(matches(isDisplayed()))

        onView(withId(R.id.tvDesignList)).check(matches(withText("Design")))
    }

    @Test
    fun upcomingEventsJVM_Universe_shouldNavigate_To_JVM_Universe_EventsList() {
        launchFragmentInContainer<UpComingFragment>(Bundle(), R.style.Theme_ProjectConf)

        onView(withId(R.id.techBannerPager)).check(matches(isDisplayed()))

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.swipeLeft())

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.swipeLeft())

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.swipeLeft())

        Thread.sleep(1000)

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.click())

        onView(withId(R.id.tvJvmUniverseList)).check(matches(isDisplayed()))

        onView(withId(R.id.tvJvmUniverseList)).check(matches(withText("JVM Universe")))
    }

    @Test
    fun upcomingEventsJS_Land_shouldNavigate_To_JS_Land_EventsList() {
        launchFragmentInContainer<UpComingFragment>(Bundle(), R.style.Theme_ProjectConf)

        onView(withId(R.id.techBannerPager)).check(matches(isDisplayed()))

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.swipeLeft())

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.swipeLeft())

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.swipeLeft())

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.swipeLeft())

        Thread.sleep(1000)

        onView(withId(R.id.techBannerPager))
            .perform(ViewActions.click())

        onView(withId(R.id.tvJSLandList)).check(matches(isDisplayed()))

        onView(withId(R.id.tvJSLandList)).check(matches(withText("JS Land")))
    }
}
