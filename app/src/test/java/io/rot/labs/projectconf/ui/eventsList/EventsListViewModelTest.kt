/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.eventsList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import io.rot.labs.projectconf.data.model.EventItem
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.utils.TestHelper
import io.rot.labs.projectconf.utils.TestSchedulerProvider
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.common.Topics
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EventsListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var networkDBHelper: NetworkDBHelper

    @Mock
    lateinit var eventsRepository: EventsRepository

    @Mock
    lateinit var upcomingEventsObserver: Observer<List<EventItem>>

    @Mock
    lateinit var archiveEventsObserver: Observer<List<EventItem>>

    @Mock
    lateinit var progressObserver: Observer<Boolean>

    lateinit var eventsListViewModel: EventsListViewModel

    lateinit var testScheduler: TestScheduler

    @Before
    fun setup() {
        testScheduler = TestScheduler()
        eventsListViewModel = EventsListViewModel(
            TestSchedulerProvider(testScheduler),
            CompositeDisposable(),
            networkDBHelper,
            eventsRepository
        )

        eventsListViewModel.apply {
            upcomingEvents.observeForever(upcomingEventsObserver)
            archiveEvents.observeForever(archiveEventsObserver)
            progress.observeForever(progressObserver)
        }
    }

    @Test
    fun getUpcomingEventsForTechTest() {
        val fakeList = TestHelper.fakeEventEntityList
        val fakeResponse = TestHelper.fakeEventItemList

        val isRefresh = false

        doReturn(Single.just(fakeList))
            .`when`(eventsRepository)
            .getUpComingEventsForTech(listOf(Topics.UX, Topics.KOTLIN), isRefresh)

        eventsListViewModel.getUpComingEventsForTech(listOf(Topics.UX, Topics.KOTLIN), isRefresh)

        testScheduler.triggerActions()

        Mockito.verify(progressObserver).onChanged(true)
        Mockito.verify(progressObserver).onChanged(false)

        Mockito.verify(upcomingEventsObserver).onChanged(fakeResponse)

        assert(eventsListViewModel.upcomingEvents.value == fakeResponse)
    }

    @Test
    fun getEventsForYearAndTechTest() {
        val fakeList = listOf(TestHelper.fakeEventEntityList[1])
        val fakeResponse = listOf(
            TestHelper.fakeEventItemList[0],
            TestHelper.fakeEventItemList[2]
        )
        val currYear = TimeDateUtils.getConfYearsList().last() - 1
        val isRefresh = false

        doReturn(Single.just(fakeList))
            .`when`(eventsRepository)
            .getEventsForYearAndTech(Topics.KOTLIN, currYear, isRefresh)

        eventsListViewModel.getEventsForYearAndTech(currYear, Topics.KOTLIN, isRefresh)

        testScheduler.triggerActions()

        Mockito.verify(progressObserver).onChanged(true)
        Mockito.verify(progressObserver).onChanged(false)

        Mockito.verify(archiveEventsObserver).onChanged(fakeResponse)

        assert(eventsListViewModel.archiveEvents.value == fakeResponse)
    }

    @After
    fun tearDown() {
        eventsListViewModel.apply {
            upcomingEvents.removeObserver(upcomingEventsObserver)
            archiveEvents.removeObserver(archiveEventsObserver)
            progress.removeObserver(progressObserver)
        }
    }
}
