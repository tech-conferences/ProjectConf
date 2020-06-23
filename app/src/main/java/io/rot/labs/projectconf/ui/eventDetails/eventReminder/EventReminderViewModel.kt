package io.rot.labs.projectconf.ui.eventDetails.eventReminder

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class EventReminderViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkDBHelper: NetworkDBHelper
) : BaseViewModel(schedulerProvider, compositeDisposable, networkDBHelper) {

    companion object {
        const val PERIOD_MONTH = "1 Month Before"
        const val PERIOD_2_WEEKS = "2 Weeks Before"
        const val PERIOD_WEEK = "1 Week Before"
        const val PERIOD_2_DAYS = "2 Days Before"

        const val milliSecondsIn1Day: Long = 86400000
        const val milliSecondsIn1Hour: Long = 3600 * 1000

        const val TIME_MORNING = "8 AM in Morning"
        const val TIME_AFTERNOON = "2 PM in Noon"
        const val TIME_EVENING = "8 PM in Evening"
    }

    val reminderDaysList = MutableLiveData<List<String>>()
    val timePeriodList = MutableLiveData<List<String>>()

    override fun onCreate() {}

    fun getReminderDayArray(cfpEndDate: Long) {
        val reminderDays = arrayListOf<String>()
        val currTime = TimeDateUtils.getCurrentDate().time
        if (currTime < cfpEndDate - (31 * milliSecondsIn1Day)) {
            reminderDays.add(PERIOD_MONTH)
        }
        if (currTime < cfpEndDate - (14 * milliSecondsIn1Day)) {
            reminderDays.add(PERIOD_2_WEEKS)
        }
        if (currTime < cfpEndDate - (7 * milliSecondsIn1Day)) {
            reminderDays.add(PERIOD_WEEK)
        }
        if (currTime < cfpEndDate - (2 * milliSecondsIn1Day)) {
            reminderDays.add(PERIOD_2_DAYS)
        }

        reminderDaysList.postValue(reminderDays)
    }

    fun getTimeList(period: String) {
        val timeList = mutableListOf<String>()
        timeList.add(TIME_MORNING)
        timeList.add(TIME_AFTERNOON)
        timeList.add(TIME_EVENING)
        timePeriodList.postValue(timeList)
    }
}
