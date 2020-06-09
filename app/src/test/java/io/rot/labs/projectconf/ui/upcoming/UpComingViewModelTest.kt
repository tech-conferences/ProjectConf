package io.rot.labs.projectconf.ui.upcoming

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import io.rot.labs.projectconf.data.model.EventBase
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.utils.TestHelper
import io.rot.labs.projectconf.utils.TestSchedulerProvider
import io.rot.labs.projectconf.utils.network.NetworkHelperImpl
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
class UpComingViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var networkHelper: NetworkHelperImpl

    @Mock
    lateinit var eventsRepository: EventsRepository

    @Mock
    lateinit var upComingEventsObserver: Observer<List<EventBase>>

    @Mock
    lateinit var progressObserver: Observer<Boolean>

    lateinit var testScheduler: TestScheduler

    lateinit var upComingViewModel: UpComingViewModel

    @Before
    fun setup() {

        testScheduler = TestScheduler()
        val compositeDisposable = CompositeDisposable()

        upComingViewModel = UpComingViewModel(
            TestSchedulerProvider(testScheduler),
            compositeDisposable,
            networkHelper,
            eventsRepository
        )

        upComingViewModel.apply {
            upcomingEvents.observeForever(upComingEventsObserver)
            progress.observeForever(progressObserver)
        }
    }

    @Test
    fun getUpComingEventsForMonthTest() {

        val fakeList = TestHelper.fakeEventEntityList
        val fakeResponse = TestHelper.fakeEventBaseList

        val isRefresh = false

        doReturn(Single.just(fakeList))
            .`when`(eventsRepository)
            .getUpComingEventsForCurrentMonth(isRefresh)

        upComingViewModel.onCreate()

        testScheduler.triggerActions()

        verify(progressObserver).onChanged(true)
        verify(progressObserver).onChanged(false)

        verify(upComingEventsObserver).onChanged(fakeResponse)

        assert(upComingViewModel.upcomingEvents.value == fakeResponse)
    }

    @After
    fun tearDown() {
        upComingViewModel.apply {
            upcomingEvents.removeObserver(upComingEventsObserver)
            progress.removeObserver(progressObserver)
        }
    }
}
