/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.eventDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import io.rot.labs.projectconf.data.local.db.entity.BookmarkedEvent
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.repository.BookmarksRepository
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.utils.TestHelper
import io.rot.labs.projectconf.utils.TestSchedulerProvider
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
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
    lateinit var networkDBHelper: NetworkDBHelper

    @Mock
    lateinit var eventsRepository: EventsRepository

    @Mock
    lateinit var bookmarksRepository: BookmarksRepository

    @Mock
    lateinit var eventDetailsObserver: Observer<EventEntity>

    @Mock
    lateinit var progressObserver: Observer<Boolean>

    @Mock
    lateinit var isBookmarkedObserver: Observer<Boolean>

    @Mock
    lateinit var cfpScheduledIdObserver: Observer<Int?>

    lateinit var testScheduler: TestScheduler

    lateinit var eventsDetailsViewModel: EventDetailsViewModel

    @Before
    fun setup() {
        testScheduler = TestScheduler()
        eventsDetailsViewModel = EventDetailsViewModel(
            TestSchedulerProvider(testScheduler),
            CompositeDisposable(),
            networkDBHelper,
            eventsRepository,
            bookmarksRepository
        )

        eventsDetailsViewModel.apply {
            progress.observeForever(progressObserver)
            eventDetails.observeForever(eventDetailsObserver)
            isBookmarked.observeForever(isBookmarkedObserver)
            cfpScheduledId.observeForever(cfpScheduledIdObserver)
        }
    }

    @Test
    fun getEventDetailsTest() {
        val fakeEventEntity = TestHelper.fakeEventEntityList[1]
        val fakeEventStartDate =
            Date(System.currentTimeMillis() - 2 * TestHelper.milliSecondsIn1Day)

        doReturn(Single.just(fakeEventEntity))
            .`when`(eventsRepository)
            .getEventDetails("Kotliners Conf", fakeEventStartDate, "kotlin")

        eventsDetailsViewModel.getEventDetails("Kotliners Conf", fakeEventStartDate, "kotlin")

        testScheduler.triggerActions()

        verify(progressObserver).onChanged(true)
        verify(progressObserver).onChanged(false)

        verify(eventDetailsObserver).onChanged(fakeEventEntity)

        assert(eventsDetailsViewModel.eventDetails.value == fakeEventEntity)
    }

    @Test
    fun checkIfBookmarkedTest() {
        val fakeEventEntity = TestHelper.fakeEventEntityList[0]

        doReturn(Maybe.just(BookmarkedEvent(fakeEventEntity)))
            .`when`(bookmarksRepository)
            .getBookmarkedEvent(
                fakeEventEntity.event.name,
                fakeEventEntity.event.startDate,
                fakeEventEntity.topic
            )

        eventsDetailsViewModel.checkIfBookmarked(
            fakeEventEntity.event.name,
            fakeEventEntity.event.startDate,
            fakeEventEntity.topic
        )

        testScheduler.triggerActions()

        verify(isBookmarkedObserver).onChanged(true)

        assert(eventsDetailsViewModel.isBookmarked.value == true)
    }

    @Test
    fun checkIfCFPScheduledTest() {
        val fakeEventEntity = TestHelper.fakeEventEntityList[0]

        val cfpTimeInMillis = TestHelper.baseTime
        doReturn(
            Maybe.just(BookmarkedEvent(fakeEventEntity, true, 23, cfpTimeInMillis))
        )
            .`when`(bookmarksRepository)
            .getBookmarkedEvent(
                fakeEventEntity.event.name,
                fakeEventEntity.event.startDate,
                fakeEventEntity.topic
            )

        eventsDetailsViewModel.checkIfCFPScheduled(
            fakeEventEntity.event.name,
            fakeEventEntity.event.startDate,
            fakeEventEntity.topic
        )

        testScheduler.triggerActions()

        verify(cfpScheduledIdObserver).onChanged(23)

        assert(eventsDetailsViewModel.cfpScheduledId.value == 23)
    }

    @Test
    fun insertBookmarkedEventTest() {
        val fakeEventEntity = TestHelper.fakeEventEntityList[0]

        val cfpTimeInMillis = TestHelper.baseTime

        val bookmarkedEvent = BookmarkedEvent(fakeEventEntity, true, 23, cfpTimeInMillis)
        doReturn(Completable.complete())
            .`when`(bookmarksRepository)
            .insertBookmarkEvent(bookmarkedEvent)

        eventsDetailsViewModel.insertBookmarkedEvent(bookmarkedEvent)

        testScheduler.triggerActions()

        verify(isBookmarkedObserver).onChanged(true)
        verify(cfpScheduledIdObserver).onChanged(23)

        assert(eventsDetailsViewModel.isBookmarked.value == true)
        assert(eventsDetailsViewModel.cfpScheduledId.value == 23)
    }

    @Test
    fun removeBookmarkedEventTest() {
        val fakeEventEntity = TestHelper.fakeEventEntityList[0]

        val cfpTimeInMillis = TestHelper.baseTime

        val bookmarkedEvent = BookmarkedEvent(fakeEventEntity, true, 23, cfpTimeInMillis)
        doReturn(Completable.complete())
            .`when`(bookmarksRepository)
            .removeBookmarkEvent(bookmarkedEvent)

        eventsDetailsViewModel.removeBookmarkedEvent(bookmarkedEvent)

        testScheduler.triggerActions()

        verify(isBookmarkedObserver).onChanged(false)

        assert(eventsDetailsViewModel.isBookmarked.value == false)
    }

    @After
    fun tearDown() {
        eventsDetailsViewModel.apply {
            progress.removeObserver(progressObserver)
            eventDetails.removeObserver(eventDetailsObserver)
            isBookmarked.observeForever(isBookmarkedObserver)
            cfpScheduledId.observeForever(cfpScheduledIdObserver)
        }
    }
}
