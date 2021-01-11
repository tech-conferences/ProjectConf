/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.alerts.alertsNotification

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import io.rot.labs.projectconf.data.model.EventItem
import io.rot.labs.projectconf.utils.TestHelper
import io.rot.labs.projectconf.utils.TestSchedulerProvider
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
class AlertsNotificationViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var networkDBHelper: NetworkDBHelper

    @Mock
    lateinit var eventsObserver: Observer<List<EventItem>>

    lateinit var alertsNotificationViewModel: AlertsNotificationViewModel

    @Before
    fun setup() {
        alertsNotificationViewModel = AlertsNotificationViewModel(
            TestSchedulerProvider(TestScheduler()),
            CompositeDisposable(),
            networkDBHelper
        )

        alertsNotificationViewModel.apply {
            events.observeForever(eventsObserver)
        }
    }

    @Test
    fun getEventsTest() {
        alertsNotificationViewModel.getEvents(TestHelper.fakeEventEntityList)
        verify(eventsObserver).onChanged(TestHelper.fakeEventItemList)

        assert(alertsNotificationViewModel.events.value == TestHelper.fakeEventItemList)
    }

    @After
    fun tearDown() {
        alertsNotificationViewModel.events.removeObserver(eventsObserver)
    }
}
