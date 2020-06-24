package io.rot.labs.projectconf.ui.eventDetails.eventReminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.background.reminders.AlarmBroadcastReceiver
import io.rot.labs.projectconf.data.local.db.entity.BookmarkedEvent
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.di.component.FragmentComponent
import io.rot.labs.projectconf.ui.base.BaseBottomSheetDialogFragment
import io.rot.labs.projectconf.ui.eventDetails.EventDetailsViewModel
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.common.Toaster
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.android.synthetic.main.fragment_event_reminder.btnReminderSet
import kotlinx.android.synthetic.main.fragment_event_reminder.daySpinner
import kotlinx.android.synthetic.main.fragment_event_reminder.layoutReminderCancel
import kotlinx.android.synthetic.main.fragment_event_reminder.layoutReminderSet
import kotlinx.android.synthetic.main.fragment_event_reminder.timeSpinner
import kotlinx.android.synthetic.main.fragment_event_reminder.tvNo
import kotlinx.android.synthetic.main.fragment_event_reminder.tvYes

class EventReminderDialogFragment : BaseBottomSheetDialogFragment<EventReminderViewModel>(),
    AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var eventDetailsViewModel: EventDetailsViewModel

    companion object {
        const val ALERT_EVENT_ENTITY = "alert_event_entity"
        const val EVENT_BUNDLE = "event_bundle"
        const val EVENT_ENTITY = "event_entity"
        const val CFP_SCHEDULED_ID = "cfp_scheduled_id"

        const val TAG = "EventReminderDialogFragment"

        const val reminderChannelId = "CFP Reminder"

        fun newInstance(
            eventEntity: EventEntity,
            cfpScheduledId: Int
        ): EventReminderDialogFragment {
            val fragment = EventReminderDialogFragment()
            val args = Bundle()
            args.putParcelable(EVENT_ENTITY, eventEntity)
            args.putInt(CFP_SCHEDULED_ID, cfpScheduledId)
            fragment.arguments = args
            return fragment
        }
    }

    private var reminderDayPeriod: String? = null
    private var reminderTimePeriod: String? = null

    override fun injectDependencies(buildComponent: FragmentComponent) {
        buildComponent.inject(this)
    }

    override fun provideLayoutId(): Int = R.layout.fragment_event_reminder

    override fun setupView(savedInstanceState: Bundle?) {

        val cfpScheduledId = requireArguments().get(CFP_SCHEDULED_ID) as Int
        if (cfpScheduledId == -1) {
            layoutReminderSet.isVisible = true
            layoutReminderCancel.isVisible = false

            val eventEntity = requireArguments().get(EVENT_ENTITY) as EventEntity
            viewModel.getReminderDayArray(eventEntity.event.cfpEndDate!!.time)

            timeSpinner.isEnabled = false

            btnReminderSet.setOnClickListener {
                // schedule alarm
                if (reminderDayPeriod != null && reminderTimePeriod != null) {
                    scheduleReminderAndSave(reminderDayPeriod!!, reminderTimePeriod!!, eventEntity)
                }
                dismiss()
            }
        } else {

            layoutReminderSet.isVisible = false
            layoutReminderCancel.isVisible = true

            tvNo.setOnClickListener {
                dismiss()
            }

            tvYes.setOnClickListener {
                val alarmManager =
                    requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val eventEntity = requireArguments().get(EVENT_ENTITY) as EventEntity

                val alarmIntent = Intent(requireContext(), AlarmBroadcastReceiver::class.java).let {
                    val bundle = Bundle()
                    bundle.putParcelable(ALERT_EVENT_ENTITY, eventEntity)
                    it.putExtra(EVENT_BUNDLE, bundle)

                    PendingIntent.getBroadcast(
                        requireContext(),
                        cfpScheduledId,
                        it,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }

                alarmManager.cancel(alarmIntent)

                eventDetailsViewModel.insertBookmarkedEvent(BookmarkedEvent(eventEntity))

                dismiss()
            }
        }
    }

    private fun scheduleReminderAndSave(
        reminderDayPeriod: String,
        reminderTimePeriod: String,
        eventEntity: EventEntity
    ) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val piReqCode =
            (eventEntity.event.cfpEndDate!!.time or Random.nextLong(0, Long.MAX_VALUE)).toInt()
        val alarmIntent = Intent(requireContext(), AlarmBroadcastReceiver::class.java).let {
            val bundle = Bundle()
            bundle.putParcelable(ALERT_EVENT_ENTITY, eventEntity)
            it.putExtra(EVENT_BUNDLE, bundle)

            PendingIntent.getBroadcast(
                requireContext(),
                piReqCode,
                it,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        var dayTimeMillis: Long = eventEntity.event.cfpEndDate.time

        val milliSecondIn1day: Long = 86400000

        when (reminderDayPeriod) {
            EventReminderViewModel.PERIOD_MONTH -> {
                dayTimeMillis -= (31 * milliSecondIn1day)
            }
            EventReminderViewModel.PERIOD_2_WEEKS -> {
                dayTimeMillis -= (14 * milliSecondIn1day)
            }
            EventReminderViewModel.PERIOD_WEEK -> {
                dayTimeMillis -= (7 * milliSecondIn1day)
            }
            EventReminderViewModel.PERIOD_2_DAYS -> {
                dayTimeMillis -= (2 * milliSecondIn1day)
            }
        }
        var timePeriodMillis: Long = TimeDateUtils.getCurrentDate().time
        val milliSecondsIn1Hour: Long = 3600 * 1000
        when (reminderTimePeriod) {
            EventReminderViewModel.TIME_MORNING -> {
                timePeriodMillis += (8 * milliSecondsIn1Hour)
            }
            EventReminderViewModel.TIME_AFTERNOON -> {
                timePeriodMillis += (14 * milliSecondsIn1Hour)
            }
            EventReminderViewModel.TIME_EVENING -> {
                timePeriodMillis += (20 * milliSecondsIn1Hour)
            }
        }

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            dayTimeMillis + timePeriodMillis,
            alarmIntent
        )

        eventDetailsViewModel.insertBookmarkedEvent(
            BookmarkedEvent(
                eventEntity,
                true,
                piReqCode,
                dayTimeMillis + timePeriodMillis
            )
        )

        Toaster.show(requireContext(), "Reminder Set")
    }

    override fun setupObservables() {
        viewModel.reminderDaysList.observe(this, Observer {
            setUpSpinner(daySpinner, it)
        })

        viewModel.timePeriodList.observe(this, Observer {
            setUpSpinner(timeSpinner, it)
        })
    }

    private fun setUpSpinner(spinner: AppCompatSpinner, spinnerItems: List<String>) {
        val adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                spinnerItems
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (val item = parent!!.getItemAtPosition(position) as String) {
            EventReminderViewModel.PERIOD_MONTH,
            EventReminderViewModel.PERIOD_2_WEEKS,
            EventReminderViewModel.PERIOD_WEEK,
            EventReminderViewModel.PERIOD_2_DAYS -> {
                reminderDayPeriod = item
                timeSpinner.isEnabled = true
                viewModel.getTimeList()
            }

            EventReminderViewModel.TIME_MORNING,
            EventReminderViewModel.TIME_AFTERNOON,
            EventReminderViewModel.TIME_EVENING -> {
                reminderTimePeriod = item
            }
        }
    }
}
