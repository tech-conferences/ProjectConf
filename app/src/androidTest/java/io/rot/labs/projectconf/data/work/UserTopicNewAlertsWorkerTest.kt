package io.rot.labs.projectconf.data.work

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.BackoffPolicy
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import io.rot.labs.projectconf.TestComponentRule
import java.util.concurrent.TimeUnit
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserTopicNewAlertsWorkerTest {

    val component = TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    @get:Rule
    val rule = RuleChain.outerRule(component)

    @Before
    fun setup() {
        val eventsRepository = component.testComponent!!.getEventRepository()
        val userTopicPreferences = component.testComponent!!.getUserTopicPreferences()
        userTopicPreferences.edit(listOf("kotlin"))

        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .setWorkerFactory(ManagerWorkerFactory(eventsRepository, userTopicPreferences))
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(component.getContext(), config)
    }

    @Test
    fun userTopicNewAlertsWorkerTest() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<UserTopicNewAlertsWorker>(7, TimeUnit.DAYS)
            .setInitialDelay(10, TimeUnit.MINUTES)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        val workManager = WorkManager.getInstance(component.getContext())
        val testDriver = WorkManagerTestInitHelper.getTestDriver(component.getContext())!!

        workManager.enqueueUniquePeriodicWork(
            UserTopicNewAlertsWorker.TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        ).result.get()

        testDriver.setAllConstraintsMet(workRequest.id)
        testDriver.setInitialDelayMet(workRequest.id)
        testDriver.setPeriodDelayMet(workRequest.id)

        val workInfo = workManager.getWorkInfoById(workRequest.id).get()

        Assert.assertThat(workInfo.state, CoreMatchers.`is`(WorkInfo.State.ENQUEUED))
    }
}
