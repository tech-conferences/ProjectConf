package io.rot.labs.projectconf.ui.main

import androidx.core.view.GravityCompat
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.TestComponentRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    val component = TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    @get:Rule
    val rule = RuleChain.outerRule(component)

    @Test
    fun onOpen_show_Upcoming_Fragment() {
        launch(MainActivity::class.java)
        onView(withId(R.id.tvScreenTitle)).check(matches(withText(R.string.upcoming_events)))
    }

    @Test
    fun onNavigateToTweets_should_showTweetFragment() {
        launch(MainActivity::class.java)
        onView(withId(R.id.drawerLayout))
            .check(matches(isClosed(GravityCompat.START)))
            .perform(DrawerActions.open())

        onView(withId(R.id.navView))
            .perform(NavigationViewActions.navigateTo(R.id.nav_tweets))

        onView(withId(R.id.tvScreenTitle)).check(matches(withText(R.string.nav_tweets)))
    }

    @Test
    fun onNavigateToAlerts_should_showAlertsFragment() {
        launch(MainActivity::class.java)
        onView(withId(R.id.drawerLayout))
            .check(matches(isClosed(GravityCompat.START)))
            .perform(DrawerActions.open())

        onView(withId(R.id.navView))
            .perform(NavigationViewActions.navigateTo(R.id.nav_alerts))

        onView(withId(R.id.tvScreenTitle)).check(matches(withText(R.string.nav_alerts)))
    }

    @Test
    fun onNavigateToBookmarks_should_showAlertsFragment() {
        launch(MainActivity::class.java)
        onView(withId(R.id.drawerLayout))
            .check(matches(isClosed(GravityCompat.START)))
            .perform(DrawerActions.open())

        onView(withId(R.id.navView))
            .perform(NavigationViewActions.navigateTo(R.id.nav_bookmarks))

        onView(withId(R.id.tvScreenTitle)).check(matches(withText(R.string.nav_bookmarks)))
    }
}
