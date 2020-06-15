package io.rot.labs.projectconf.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.utils.TestHelper
import io.rot.labs.projectconf.utils.TestSchedulerProvider
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
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
class SearchViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var networkDBHelper: NetworkDBHelper

    @Mock
    lateinit var eventsRepository: EventsRepository

    @Mock
    lateinit var eventsObserver: Observer<List<EventEntity>>

    @Mock
    lateinit var progressObserver: Observer<Boolean>

    @Mock
    lateinit var nameQueryObserver: Observer<String>

    lateinit var testScheduler: TestScheduler

    lateinit var searchViewModel: SearchViewModel

    @Before
    fun setup() {
        testScheduler = TestScheduler()
        searchViewModel = SearchViewModel(
            TestSchedulerProvider(testScheduler),
            CompositeDisposable(),
            networkDBHelper,
            eventsRepository
        )

        searchViewModel.apply {
            events.observeForever(eventsObserver)
            progress.observeForever(progressObserver)
            nameQueryHolder.observeForever(nameQueryObserver)
        }
    }

    @Test
    fun getRecentEventsByQueryTest() {
        val fakeEventEntityList = listOf(TestHelper.fakeEventEntityList[0])

        val yearsList = TimeDateUtils.getConfYearsList()
        val recentYears = listOf(yearsList.last() - 1, yearsList.last())
        doReturn(Single.just(fakeEventEntityList))
            .`when`(eventsRepository)
            .getRecentEventsByQuery("Pragma", recentYears)

        searchViewModel.getRecentEventsByQuery("Pragma", recentYears)

        testScheduler.triggerActions()

        verify(progressObserver).onChanged(true)
        verify(progressObserver).onChanged(false)

        verify(eventsObserver).onChanged(fakeEventEntityList)

        verify(nameQueryObserver).onChanged("Pragma")
    }

    @Test
    fun getRecentEventsByQuery_emptyQuery_test() {
        val yearsList = TimeDateUtils.getConfYearsList()
        val recentYears = listOf(yearsList.last() - 1, yearsList.last())
        searchViewModel.getRecentEventsByQuery("", recentYears)

        verify(nameQueryObserver).onChanged("")
    }

    @After
    fun tearDown() {
        searchViewModel.apply {
            events.removeObserver(eventsObserver)
            progress.removeObserver(progressObserver)
            nameQueryHolder.removeObserver(nameQueryObserver)
        }
    }
}
