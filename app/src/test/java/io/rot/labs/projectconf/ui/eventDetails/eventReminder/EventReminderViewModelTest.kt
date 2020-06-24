package io.rot.labs.projectconf.ui.eventDetails.eventReminder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import io.rot.labs.projectconf.utils.TestSchedulerProvider
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EventReminderViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var networkDBHelper: NetworkDBHelper

    @Mock
    lateinit var reminderDayListObserver: Observer<List<String>>

    @Mock
    lateinit var timePeriodListObserver: Observer<List<String>>

    lateinit var eventReminderViewModel: EventReminderViewModel

    @Before
    fun setup() {
        eventReminderViewModel = EventReminderViewModel(
            TestSchedulerProvider(TestScheduler()),
            CompositeDisposable(),
            networkDBHelper
        )

        eventReminderViewModel.apply {
            reminderDaysList.observeForever(reminderDayListObserver)
            timePeriodList.observeForever(timePeriodListObserver)
        }
    }

    @Test
    fun getReminderDayArrayTest() {
        val cfpEndDate =
            TimeDateUtils.getCurrentDate().time + EventReminderViewModel.milliSecondsIn1Day * 62

        eventReminderViewModel.getReminderDayArray(cfpEndDate)

        verify(reminderDayListObserver).onChanged(
            listOf(
                EventReminderViewModel.PERIOD_MONTH,
                EventReminderViewModel.PERIOD_2_WEEKS,
                EventReminderViewModel.PERIOD_WEEK,
                EventReminderViewModel.PERIOD_2_DAYS
            )
        )

        assert(
            eventReminderViewModel.reminderDaysList.value == listOf(
                EventReminderViewModel.PERIOD_MONTH,
                EventReminderViewModel.PERIOD_2_WEEKS,
                EventReminderViewModel.PERIOD_WEEK,
                EventReminderViewModel.PERIOD_2_DAYS
            )
        )
    }

    @Test
    fun getTimeListTest() {
        eventReminderViewModel.getTimeList()

        verify(timePeriodListObserver).onChanged(
            listOf(
                EventReminderViewModel.TIME_MORNING,
                EventReminderViewModel.TIME_AFTERNOON,
                EventReminderViewModel.TIME_EVENING
            )
        )
    }

    @After
    fun tearDown() {
        eventReminderViewModel.apply {
            reminderDaysList.removeObserver(reminderDayListObserver)
            timePeriodList.removeObserver(timePeriodListObserver)
        }
    }
}
