/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.alerts.alertsView

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import io.rot.labs.projectconf.data.local.prefs.UserTopicPreferences
import io.rot.labs.projectconf.utils.TestSchedulerProvider
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
class AlertsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var userTopicPreferences: UserTopicPreferences

    @Mock
    lateinit var networkDBHelper: NetworkDBHelper

    @Mock
    lateinit var userTopicsObserver: Observer<List<String>>

    lateinit var alertsViewModel: AlertsViewModel

    @Before
    fun setup() {
        alertsViewModel = AlertsViewModel(
            TestSchedulerProvider(TestScheduler()),
            CompositeDisposable(),
            networkDBHelper,
            userTopicPreferences
        )

        alertsViewModel.apply {
            userTopics.observeForever(userTopicsObserver)
        }
    }

    @Test
    fun getTopicPreferencesTest() {
        doReturn(listOf("kotlin"))
            .`when`(userTopicPreferences)
            .getUserTopics()

        alertsViewModel.onCreate()

        verify(userTopicsObserver).onChanged(listOf("kotlin"))

        assert(alertsViewModel.userTopics.value == listOf("kotlin"))
    }

    @After
    fun tearDown() {
        alertsViewModel.apply {
            userTopics.removeObserver(userTopicsObserver)
        }
    }
}
