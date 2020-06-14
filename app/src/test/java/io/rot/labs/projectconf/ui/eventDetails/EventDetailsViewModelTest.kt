package io.rot.labs.projectconf.ui.eventDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.utils.TestHelper
import io.rot.labs.projectconf.utils.TestSchedulerProvider
import io.rot.labs.projectconf.utils.network.NetworkHelper
import java.util.Date
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EventDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var networkHelper: NetworkHelper

    @Mock
    lateinit var eventsRepository: EventsRepository

    @Mock
    lateinit var eventDetailsObserver: Observer<EventEntity>

    @Mock
    lateinit var progressObserver: Observer<Boolean>

    lateinit var testScheduler: TestScheduler

    lateinit var eventsDetailsViewModel: EventDetailsViewModel

    @Before
    fun setup() {
        testScheduler = TestScheduler()
        eventsDetailsViewModel = EventDetailsViewModel(
            TestSchedulerProvider(testScheduler),
            CompositeDisposable(),
            networkHelper,
            eventsRepository
        )

        eventsDetailsViewModel.apply {
            progress.observeForever(progressObserver)
            eventDetails.observeForever(eventDetailsObserver)
        }
    }

    @Test
    fun getEventDetailsTest() {
        val fakeEventEntity = TestHelper.fakeEventEntityList[1]
        val fakeEventStartDate =
            Date(System.currentTimeMillis() - 2 * TestHelper.milliSecondsIn1Day)

        doReturn(Single.just(fakeEventEntity))
            .`when`(eventsRepository)
            .getEventDetails("Kotliners Conf", fakeEventStartDate)

        eventsDetailsViewModel.getEventDetails("Kotliners Conf", fakeEventStartDate)

        testScheduler.triggerActions()

        verify(progressObserver).onChanged(true)
        verify(progressObserver).onChanged(false)

        verify(eventDetailsObserver).onChanged(fakeEventEntity)

        assert(eventsDetailsViewModel.eventDetails.value == fakeEventEntity)
    }

    @After
    fun tearDown() {
        eventsDetailsViewModel.apply {
            progress.removeObserver(progressObserver)
            eventDetails.removeObserver(eventDetailsObserver)
        }
    }
}
