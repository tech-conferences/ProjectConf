package io.rot.labs.projectconf.ui.eventDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.data.local.db.entity.BookmarkedEvent
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.di.component.ActivityComponent
import io.rot.labs.projectconf.ui.base.BaseActivity
import io.rot.labs.projectconf.utils.common.TimeDateUtils
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
                openURL(it, false)
            }
        }

        btnConfUrl.setOnClickListener {
            eventDetail?.event?.url?.let {
                openURL(it, false)
            }
        }

        btnTwitter.setOnClickListener {
            eventDetail?.event?.twitter?.let {
                val twitterUrl = "https://twitter.com/${it.apply { removePrefix("@") }}"
                openURL(twitterUrl, true)
            }
        }

        tvCFPEndDate.setOnClickListener {
            // show Add reminder bottom frag
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
                viewModel.removeBookmarkedEvent(BookmarkedEvent(eventDetail!!))
            } else {
                viewModel.insertBookmarkedEvent(BookmarkedEvent(eventDetail!!))
            }
        }
    }

    override fun setupObservables() {
        super.setupObservables()

        viewModel.eventDetails.observe(this, Observer {

            eventDetail = it

            viewModel.checkIfBookmarked(it.event.name, it.event.startDate, it.topic)

            ImageUtils.loadImageDrawable(this, ImageUtils.getTopicDrawableResId(it.topic), ivTech)

            tvGenericTitle.text = it.event.name

            tvGenericSub.text = if (it.event.country == "Online") {
                it.event.country
            } else {
                "${it.event.city}, ${it.event.country}"
            }

            val startDate = TimeDateUtils.getFormattedDay(it.event.startDate)
            val endDate = TimeDateUtils.getFormattedDay(it.event.endDate)
            tvEventStartDate.text = startDate

            if (startDate == endDate) {
                tvDateSeparator.isVisible = false
                tvEventEndDate.isVisible = false
            } else {
                tvEventEndDate.text = endDate
            }

            if (it.event.twitter != null) {
                btnTwitter.isVisible = true
                btnTwitter.text = it.event.twitter
            } else {
                btnTwitter.isVisible = false
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

                if (it.event.cfpEndDate.time > TimeDateUtils.getCurrentDate().time + 43_200_000) {
                    tvCFPEndDate.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        ContextCompat.getDrawable(this, R.drawable.ic_add_alert),
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

                if (it.event.cfpEndDate.time > TimeDateUtils.getCurrentDate().time + 43_200_000) {
                    tvCFPEndDate.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        ContextCompat.getDrawable(this, R.drawable.ic_add_alert),
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
    }

    private fun openURL(url: String, isTwitter: Boolean) {
        val urlIntent = CustomTabsIntent.Builder().apply {
            val colorId = if (isTwitter) {
                R.color.blueTwitter
            } else {
                R.color.yellow500
            }
            setToolbarColor(ContextCompat.getColor(this@EventDetailsActivity, colorId))
            addDefaultShareMenuItem()
            setShowTitle(true)
        }.build()
        urlIntent.launchUrl(this, Uri.parse(url))
    }
}
