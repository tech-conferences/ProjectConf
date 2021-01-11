/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.background.reminders

import android.content.BroadcastReceiver
import android.content.Intent
import android.os.Bundle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.rot.labs.projectconf.TestComponentRule
import io.rot.labs.projectconf.ui.eventDetails.eventReminder.EventReminderDialogFragment
import io.rot.labs.projectconf.utils.testHelper.AndroidTestHelper
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlarmBroadcastReceiverTest {

    val component =
        TestComponentRule(InstrumentationRegistry.getInstrumentation().targetContext)

    @get:Rule
    val rule = RuleChain.outerRule(component)

    lateinit var alarmBroadcastReceiver: BroadcastReceiver

    @Before
    fun setup() {
        alarmBroadcastReceiver = AlarmBroadcastReceiver()
    }

    @Test
    fun test_cfp_reminder_intent() {
        val intent = Intent(component.getContext(), AlarmBroadcastReceiver::class.java).apply {
            val bundle = Bundle()
            bundle.putParcelable(
                EventReminderDialogFragment.ALERT_EVENT_ENTITY,
                AndroidTestHelper.fakeEventEntityList[1]
            )
            putExtra(EventReminderDialogFragment.EVENT_BUNDLE, bundle)
        }

        alarmBroadcastReceiver.onReceive(component.getContext(), intent)
    }

    @Test
    fun test_boot_receive_intent() {
        val intent = Intent().apply {
            action = "android.intent.action.BOOT_COMPLETED"
        }
        alarmBroadcastReceiver.onReceive(component.getContext(), intent)
    }
}
