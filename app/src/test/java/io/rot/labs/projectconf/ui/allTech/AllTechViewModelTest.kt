package io.rot.labs.projectconf.ui.allTech

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import io.rot.labs.projectconf.utils.TestSchedulerProvider
import io.rot.labs.projectconf.utils.common.TopicsList
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
class AllTechViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var networkDBHelper: NetworkDBHelper

    @Mock
    lateinit var allTechTopicsObserver: Observer<List<Pair<String, Int?>>>

    lateinit var allTechViewModel: AllTechViewModel

    @Before
    fun setup() {
        allTechViewModel = AllTechViewModel(
            TestSchedulerProvider(TestScheduler()),
            CompositeDisposable(),
            networkDBHelper
        )

        allTechViewModel.allTechTopics.observeForever(allTechTopicsObserver)
    }

    @Test
    fun getTopicsListForYear_isArchive() {

        val year = 2017
        val expectedList = TopicsList.YEAR_2017

        allTechViewModel.getTopicsListForYear(listOf(year), year < 2020)

        val response = allTechViewModel.mapToPairedYearList(expectedList, year < 2020, year)

        verify(allTechTopicsObserver).onChanged(response)

        assert(allTechViewModel.allTechTopics.value == response)
    }

    @Test
    fun getTopicsListForYear_isNotArchive() {

        val year = 2021
        val expectedList = TopicsList.YEAR_2021

        allTechViewModel.getTopicsListForYear(listOf(year), year < 2020)

        val response = allTechViewModel.mapToPairedYearList(expectedList, year < 2020, year)

        verify(allTechTopicsObserver).onChanged(response)

        assert(allTechViewModel.allTechTopics.value == response)
    }

    @After
    fun tearDown() {
        allTechViewModel.allTechTopics.removeObserver(allTechTopicsObserver)
    }
}
