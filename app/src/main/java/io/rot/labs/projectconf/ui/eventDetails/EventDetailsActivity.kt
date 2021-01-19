/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.eventDetails

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.background.reminders.AlarmBroadcastReceiver
import io.rot.labs.projectconf.data.local.db.entity.BookmarkedEvent
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.di.component.ActivityComponent
import io.rot.labs.projectconf.ui.base.BaseActivity
import io.rot.labs.projectconf.ui.eventDetails.eventReminder.EventReminderDialogFragment
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.common.Toaster
import io.rot.labs.projectconf.utils.common.URLOpener.openURL
import io.rot.labs.projectconf.utils.display.ImageUtils
import java.util.Date
import java.util.Locale
import kotlinx.android.synthetic.main.activity_event_details.appBarLayoutEventDetails
import kotlinx.android.synthetic.main.activity_event_details.fabBookMark
import kotlinx.android.synthetic.main.activity_event_details.ivAddToCalendar
import kotlinx.android.synthetic.main.activity_event_details.ivShare
import kotlinx.android.synthetic.main.activity_event_details.layoutEventDetails
import kotlinx.android.synthetic.main.activity_event_details.matToolBarEventDetails
import kotlinx.android.synthetic.main.activity_event_details.shimmerEventDetails
import kotlinx.android.synthetic.main.activity_events_list.collapsingToolbarLayout
import kotlinx.android.synthetic.main.layout_event_details.btnCfpUrl
import kotlinx.android.synthetic.main.layout_event_details.btnConfUrl
import kotlinx.android.synthetic.main.layout_event_details.btnTwitter
import kotlinx.android.synthetic.main.layout_event_details.tvCFPEndDate
import kotlinx.android.synthetic.main.layout_event_details.tvCFPEndDateHeader
import kotlinx.android.synthetic.main.layout_event_details.tvCFPHeader
import kotlinx.android.synthetic.main.layout_event_details.tvDateSeparator
import kotlinx.android.synthetic.main.layout_event_details.tvEventEndDate
import kotlinx.android.synthetic.main.layout_event_details.tvEventStartDate
import kotlinx.android.synthetic.main.layout_generic_banner.ivTech
import kotlinx.android.synthetic.main.layout_generic_banner.tvGenericSub
import kotlinx.android.synthetic.main.layout_generic_banner.tvGenericTitle

class EventDetailsActivity : BaseActivity<EventDetailsViewModel>() {

    companion object {
        const val EVENT_NAME = "event_name"
        const val EVENT_START_DATE = "event_start_date"
        const val EVENT_TOPIC = "event_topic"
    }

    private var eventDetail: EventEntity? = null

    private var isBookMarked: Boolean = false
    private var cfpScheduledId: Int = -1

    private val milliSecondsIn1Day = 86400000

    override fun injectDependencies(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun provideLayoutId(): Int = R.layout.activity_event_details

    override fun setupView(savedInstanceState: Bundle?) {

        setSupportActionBar(matToolBarEventDetails)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        setupEventDetails()

        setupCollapsingToolbarLayout()

        btnCfpUrl.setOnClickListener {
            eventDetail?.event?.cfpUrl?.let {
                openURL(it, false, this)
            }
        }

        btnConfUrl.setOnClickListener {
            eventDetail?.event?.url?.let {
                openURL(it, false, this)
            }
        }

        btnTwitter.setOnClickListener {
            eventDetail?.event?.twitter?.let {
                val twitterUrl = "https://twitter.com/${it.removePrefix("@")}"
                openURL(twitterUrl, true, this)
            }
        }

        tvCFPEndDate.setOnClickListener {
            // show Add reminder bottom frag
            eventDetail?.let {
                EventReminderDialogFragment.newInstance(it, cfpScheduledId)
                    .show(supportFragmentManager, EventReminderDialogFragment.TAG)
            }
        }

        ivAddToCalendar.setOnClickListener {
            eventDetail?.let {
                val calendarIntent = Intent(
                    Intent.ACTION_INSERT,
                    CalendarContract.Events.CONTENT_URI
                ).apply {
                    putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, it.event.startDate.time)

                    if (it.event.endDate.time == it.event.startDate.time) {
                        putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                        putExtra(
                            CalendarContract.EXTRA_EVENT_END_TIME,
                            it.event.endDate.time + milliSecondsIn1Day
                        )
                    } else {
                        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, it.event.endDate.time)
                    }

                    putExtra(CalendarContract.Events.TITLE, it.event.name)
                    putExtra(CalendarContract.Events.DESCRIPTION, "Conference link ${it.event.url}")
                    putExtra(
                        CalendarContract.Events.EVENT_LOCATION,
                        if (it.event.country == "Online") {
                            it.event.country
                        } else {
                            "${it.event.city}, ${it.event.country}"
                        }
                    )
                }
                val calendarChooserIntent = Intent.createChooser(calendarIntent, "Add to Calendar")
                startActivity(calendarChooserIntent)
            }
        }
        ivShare.setOnClickListener {
            eventDetail?.let {
                val shareIntent = Intent.createChooser(Intent().apply {

                    val title = "${it.event.name}, ${it.event.country}"
                    val text = "${it.event.name} \n ${it.event.url}"
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, text)
                    putExtra(Intent.EXTRA_TITLE, title)
                    type = "text/plain"
                }, "Conference Details")
                startActivity(shareIntent)
            }
        }

        fabBookMark.setOnClickListener {
            if (isBookMarked) {
                if (cfpScheduledId != -1 && eventDetail != null) {
                    removeReminder()
                }
                viewModel.removeBookmarkedEvent(BookmarkedEvent(eventDetail!!))
            } else {
                viewModel.insertBookmarkedEvent(BookmarkedEvent(eventDetail!!))
            }
        }
    }

    private fun removeReminder() {
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val alarmIntent = Intent(this, AlarmBroadcastReceiver::class.java).let {
            val bundle = Bundle()
            bundle.putParcelable(EventReminderDialogFragment.ALERT_EVENT_ENTITY, eventDetail!!)
            it.putExtra(EventReminderDialogFragment.EVENT_BUNDLE, bundle)

            PendingIntent.getBroadcast(
                this,
                cfpScheduledId,
                it,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        alarmManager.cancel(alarmIntent)
        Toaster.show(this, "Reminder Cancel")
    }

    override fun setupObservables() {
        super.setupObservables()

        viewModel.eventDetails.observe(this, Observer {

            eventDetail = it

            viewModel.checkIfBookmarked(it.event.name, it.event.startDate, it.topic)

            ImageUtils.loadImageDrawable(this, ImageUtils.getTopicDrawableResId(it.topic), ivTech)

            tvGenericTitle.text = it.event.name

            tvGenericSub.text = if (it.event.online) {
                "Online"
            } else if (it.event.city != null && it.event.country != null) {
                "${it.event.city}, ${it.event.country}"
            } else {
                ""
            }

            // Event Dates section
            val startDate = TimeDateUtils.getFormattedDay(it.event.startDate)
            val endDate = TimeDateUtils.getFormattedDay(it.event.endDate)
            tvEventStartDate.text = startDate

            if (startDate == endDate) {
                tvDateSeparator.isVisible = false
                tvEventEndDate.isVisible = false
            } else {
                tvEventEndDate.text = endDate
            }

            // Twitter button section
            if (it.event.twitter != null) {
                btnTwitter.isVisible = true
                btnTwitter.text = it.event.twitter
            } else {
                btnTwitter.isVisible = false
            }

            // CFP Section
            val cfpDrawable = if (cfpScheduledId != -1) {
                getDrawable(R.drawable.ic_delete_alert)
            } else {
                getDrawable(R.drawable.ic_add_alert)
            }

            if (it.event.cfpEndDate == null && it.event.cfpUrl == null) {
                tvCFPHeader.isVisible = false
                tvCFPEndDate.isVisible = false
                tvCFPEndDateHeader.isVisible = false
                btnCfpUrl.isVisible = false
            } else if (it.event.cfpUrl == null) {
                btnCfpUrl.isVisible = false

                tvCFPHeader.isVisible = true
                tvCFPEndDate.isVisible = true
                tvCFPEndDateHeader.isVisible = true

                tvCFPEndDate.text = TimeDateUtils.getFormattedDay(it.event.cfpEndDate!!)

                if (it.event.cfpEndDate.time > TimeDateUtils.getCurrentDate().time + (3 * 86_400_000)) {
                    tvCFPEndDate.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        cfpDrawable,
                        null
                    )
                } else {
                    tvCFPEndDate.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        null,
                        null
                    )
                    tvCFPEndDate.isClickable = false
                }
            } else if (it.event.cfpEndDate == null) {
                btnCfpUrl.isVisible = true
                tvCFPHeader.isVisible = true
            } else {
                btnCfpUrl.isVisible = true
                tvCFPHeader.isVisible = true
                tvCFPEndDateHeader.isVisible = true
                tvCFPEndDate.isVisible = true
                tvCFPEndDate.text = TimeDateUtils.getFormattedDay(it.event.cfpEndDate)

                if (it.event.cfpEndDate.time > TimeDateUtils.getCurrentDate().time + (3 * 86_400_000)) {
                    tvCFPEndDate.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        cfpDrawable,
                        null
                    )
                } else {
                    tvCFPEndDate.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        null,
                        null
                    )
                    tvCFPEndDate.isClickable = false
                }
            }
        })

        viewModel.progress.observe(this, Observer {
            if (it) {
                layoutEventDetails.isVisible = false
                shimmerEventDetails.apply {
                    isVisible = true
                    startShimmer()
                }
            } else {
                layoutEventDetails.isVisible = true
                shimmerEventDetails.apply {
                    isVisible = false
                    stopShimmer()
                }
            }
        })

        viewModel.isBookmarked.observe(this, Observer {
            isBookMarked = it
            if (it) {
                fabBookMark.setImageDrawable(getDrawable(R.drawable.ic_bookmark))
            } else {
                fabBookMark.setImageDrawable(getDrawable(R.drawable.ic_bookmark_border))
            }
        })

        viewModel.cfpScheduledId.observe(this, Observer {
            cfpScheduledId = it ?: -1
            if (it != null) {
                tvCFPEndDate.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    getDrawable(R.drawable.ic_delete_alert),
                    null
                )
            } else {
                tvCFPEndDate.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    getDrawable(R.drawable.ic_add_alert),
                    null
                )
            }
        })
    }

    private fun setupCollapsingToolbarLayout() {
        val title = intent.getStringExtra(EVENT_NAME)
        var isShow = true
        var scrollRange = -1
        appBarLayoutEventDetails.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                collapsingToolbarLayout.title = title!!.toUpperCase(Locale.ENGLISH)
                isShow = true
            } else if (isShow) {
                // careful there should a space between double quote otherwise it wont work
                collapsingToolbarLayout.title = " "
                isShow = false
            }
        })
    }

    private fun setupEventDetails() {
        val eventName = intent.getStringExtra(EVENT_NAME)
        val eventStartDateLong = intent.getLongExtra(EVENT_START_DATE, -1)
        val eventTopic = intent.getStringExtra(EVENT_TOPIC)
        viewModel.getEventDetails(eventName!!, Date(eventStartDateLong), eventTopic!!)
        viewModel.checkIfCFPScheduled(eventName, Date(eventStartDateLong), eventTopic)
    }
}
