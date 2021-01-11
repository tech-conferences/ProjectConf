/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.eventsList

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.ActivityComponent
import io.rot.labs.projectconf.ui.archive.ArchiveActivity
import io.rot.labs.projectconf.ui.base.BaseActivity
import io.rot.labs.projectconf.ui.eventsItem.EventsItemAdapter
import io.rot.labs.projectconf.utils.common.Resource
import io.rot.labs.projectconf.utils.common.Toaster
import io.rot.labs.projectconf.utils.common.Topics
import io.rot.labs.projectconf.utils.display.ImageUtils
import java.util.Locale
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_events_list.appBarLayoutEventsList
import kotlinx.android.synthetic.main.activity_events_list.collapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_events_list.headerContainer
import kotlinx.android.synthetic.main.activity_events_list.layoutError
import kotlinx.android.synthetic.main.activity_events_list.layoutListIsEmpty
import kotlinx.android.synthetic.main.activity_events_list.layoutNoConnection
import kotlinx.android.synthetic.main.activity_events_list.matToolBarEventList
import kotlinx.android.synthetic.main.activity_events_list.rvEvents
import kotlinx.android.synthetic.main.activity_events_list.shimmerEventsList
import kotlinx.android.synthetic.main.activity_events_list.swipeRefreshList
import kotlinx.android.synthetic.main.layout_design_list_banner.view.ivDesignListCard
import kotlinx.android.synthetic.main.layout_design_list_banner.view.tvDesignSub
import kotlinx.android.synthetic.main.layout_devops_list_banner.view.ivDevOpsListCard
import kotlinx.android.synthetic.main.layout_devops_list_banner.view.tvCloudDevOpsSub
import kotlinx.android.synthetic.main.layout_error.btnErrorRetry
import kotlinx.android.synthetic.main.layout_error.tvFatalError
import kotlinx.android.synthetic.main.layout_generic_banner.view.ivTech
import kotlinx.android.synthetic.main.layout_generic_banner.view.tvGenericSub
import kotlinx.android.synthetic.main.layout_generic_banner.view.tvGenericTitle
import kotlinx.android.synthetic.main.layout_js_land_list_banner.view.ivJavaScriptListCard
import kotlinx.android.synthetic.main.layout_js_land_list_banner.view.ivTypeScriptListCard
import kotlinx.android.synthetic.main.layout_js_land_list_banner.view.tvJSLandSub
import kotlinx.android.synthetic.main.layout_jvm_universe_list_banner.view.ivGroovyListCard
import kotlinx.android.synthetic.main.layout_jvm_universe_list_banner.view.ivJavaListCard
import kotlinx.android.synthetic.main.layout_jvm_universe_list_banner.view.ivKotlinListCard
import kotlinx.android.synthetic.main.layout_jvm_universe_list_banner.view.ivScalaListCard
import kotlinx.android.synthetic.main.layout_jvm_universe_list_banner.view.tvJvmUniverseSub
import kotlinx.android.synthetic.main.layout_list_is_empty.view.tvListIsEmpty
import kotlinx.android.synthetic.main.layout_mobile_dev_list_banner.view.ivAndroidListCard
import kotlinx.android.synthetic.main.layout_mobile_dev_list_banner.view.ivIosListCard
import kotlinx.android.synthetic.main.layout_mobile_dev_list_banner.view.ivKotlinMBListCard
import kotlinx.android.synthetic.main.layout_mobile_dev_list_banner.view.tvMobileDevSub
import kotlinx.android.synthetic.main.layout_no_connection.btnNoConnectionRetry
import kotlinx.android.synthetic.main.layout_no_connection.tvNoInternet

class EventsListActivity : BaseActivity<EventsListViewModel>() {

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var gridLayoutManager: GridLayoutManager

    @Inject
    lateinit var eventsItemAdapter: EventsItemAdapter

    companion object {
        const val TOPIC_TITLE = "topic_title"
        const val TOPIC_SUB = "topic_sub"
        const val TOPICS_LIST = "topics_list"
        const val IS_ARCHIVE = "is_archive"
        const val ARCHIVE_YEAR = "archive_year"
    }

    override fun injectDependencies(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun provideLayoutId(): Int = R.layout.activity_events_list

    override fun setupView(savedInstanceState: Bundle?) {

        setSupportActionBar(matToolBarEventList)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        setupCollapsingToolBarLayout()
        setupRecyclerView()
        setupSwipeRefreshLayout()
        setupErrorLayouts()

        val topicsList = intent.getStringArrayListExtra(TOPICS_LIST)
        val isArchive = intent.getBooleanExtra(IS_ARCHIVE, false)
        if (!isArchive) {
            viewModel.getUpComingEventsForTech(topicsList!!)
        } else {
            val year = intent.getIntExtra(ARCHIVE_YEAR, -1)
            viewModel.getEventsForYearAndTech(year, topicsList!![0])
        }
    }

    override fun setupObservables() {

        viewModel.messageStringId.observe(this, Observer {
            it.data?.let { data ->
                if (data == R.string.network_internet_not_connected || data == R.string.network_could_not_connect) {
                    if (viewModel.upcomingEvents.value?.isNotEmpty() == true) {
                        Toaster.show(this, getString(R.string.could_not_refresh))
                    } else {
                        layoutNoConnection.isVisible = true
                        layoutError.isVisible = false
                        shimmerEventsList.isVisible = false
                        swipeRefreshList.isVisible = false
                        tvNoInternet.text = getString(it.data)
                    }
                } else {
                    if (viewModel.upcomingEvents.value?.isNotEmpty() == true) {
                        Toaster.show(this, getString(R.string.could_not_refresh))
                    } else {
                        layoutError.isVisible = true
                        swipeRefreshList.isVisible = false
                        layoutNoConnection.isVisible = false
                        shimmerEventsList.isVisible = false

                        tvFatalError.text = getString(data)
                    }
                }
                viewModel.messageStringId.postValue(Resource.error(null))
            }
        })

        viewModel.messageString.observe(this, Observer {
            it.data?.let { data ->
                layoutError.isVisible = true
                layoutNoConnection.isVisible = false
                shimmerEventsList.isVisible = false
                swipeRefreshList.isVisible = false

                tvFatalError.text = data

                viewModel.messageString.postValue(Resource.error(null))
            }
        })

        viewModel.upcomingEvents.observe(this, Observer {
            if (it.isEmpty()) {
                layoutListIsEmpty.isVisible = true
                setupListEmptyLayout()
                layoutNoConnection.isVisible = false
                layoutError.isVisible = false
                rvEvents.isVisible = false
            } else {
                eventsItemAdapter.updateData(it)
            }
        })

        viewModel.archiveEvents.observe(this, Observer {
            if (it.isEmpty()) {
                layoutListIsEmpty.isVisible = true
                setupListEmptyLayout()
                layoutNoConnection.isVisible = false
                layoutError.isVisible = false
                rvEvents.isVisible = false
            } else {
                eventsItemAdapter.updateData(it)
            }
        })

        viewModel.progress.observe(this, Observer {
            if (it) {
                swipeRefreshList.isVisible = false
                layoutError.isVisible = false
                layoutNoConnection.isVisible = false

                shimmerEventsList.apply {
                    isVisible = true
                    startShimmer()
                }
            } else {
                swipeRefreshList.isVisible = true
                swipeRefreshList.isRefreshing = false
                layoutError.isVisible = false
                layoutNoConnection.isVisible = false

                shimmerEventsList.apply {
                    isVisible = false
                    stopShimmer()
                }
            }
        })
    }

    private fun setupCollapsingToolBarLayout() {
        val topicTitle = intent.getStringExtra(TOPIC_TITLE)
        val topicSub = intent.getStringExtra(TOPIC_SUB)
        var isShow = true
        var scrollRange = -1
        appBarLayoutEventsList.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                collapsingToolbarLayout.title = topicTitle!!.toUpperCase(Locale.ROOT)
                isShow = true
            } else if (isShow) {
                // careful there should a space between double quote otherwise it wont work
                collapsingToolbarLayout.title = " "
                isShow = false
            }
        })

        when (topicTitle) {
            getString(R.string.mobile_dev_label) -> {
                val inflater = LayoutInflater.from(this)
                val view =
                    inflater.inflate(R.layout.layout_mobile_dev_list_banner, headerContainer, false)
                view.tvMobileDevSub.text =
                    String.format(getString(R.string.tech_conf_sub), topicSub)

                ImageUtils.loadImageDrawable(this, R.drawable.kotlin_logo, view.ivKotlinMBListCard)
                ImageUtils.loadImageDrawable(this, R.drawable.ios_logo, view.ivIosListCard)
                ImageUtils.loadImageDrawable(this, R.drawable.android_logo, view.ivAndroidListCard)

                headerContainer.addView(view)
            }
            getString(R.string.cloud_devops_label) -> {
                val inflater = LayoutInflater.from(this)
                val view =
                    inflater.inflate(R.layout.layout_devops_list_banner, headerContainer, false)
                view.tvCloudDevOpsSub.text =
                    String.format(getString(R.string.tech_conf_sub), topicSub)

                ImageUtils.loadImageDrawable(this, R.drawable.devops_logo, view.ivDevOpsListCard)

                headerContainer.addView(view)
            }
            getString(R.string.jvm_universe_label) -> {
                val inflater = LayoutInflater.from(this)
                val view =
                    inflater.inflate(
                        R.layout.layout_jvm_universe_list_banner,
                        headerContainer,
                        false
                    )
                view.tvJvmUniverseSub.text =
                    String.format(getString(R.string.tech_conf_sub), topicSub)

                ImageUtils.loadImageDrawable(this, R.drawable.kotlin_logo, view.ivKotlinListCard)
                ImageUtils.loadImageDrawable(this, R.drawable.java_logo, view.ivJavaListCard)
                ImageUtils.loadImageDrawable(this, R.drawable.scala_logo, view.ivScalaListCard)
                ImageUtils.loadImageDrawable(this, R.drawable.groovy_logo, view.ivGroovyListCard)

                headerContainer.addView(view)
            }
            getString(R.string.design_label) -> {
                val inflater = LayoutInflater.from(this)
                val view =
                    inflater.inflate(R.layout.layout_design_list_banner, headerContainer, false)
                view.tvDesignSub.text =
                    String.format(getString(R.string.tech_conf_sub), topicSub)

                ImageUtils.loadImageDrawable(this, R.drawable.ux_logo, view.ivDesignListCard)

                headerContainer.addView(view)
            }
            getString(R.string.js_land_label) -> {
                val inflater = LayoutInflater.from(this)
                val view =
                    inflater.inflate(R.layout.layout_js_land_list_banner, headerContainer, false)
                view.tvJSLandSub.text =
                    String.format(getString(R.string.tech_conf_sub), topicSub)

                ImageUtils.loadImageDrawable(
                    this,
                    R.drawable.javascript_logo,
                    view.ivJavaScriptListCard
                )
                ImageUtils.loadImageDrawable(
                    this,
                    R.drawable.typescript_logo,
                    view.ivTypeScriptListCard
                )

                headerContainer.addView(view)
            }

            Topics.ANDROID -> addBannerForTech(Topics.ANDROID, topicSub!!)

            Topics.CLOJURE -> addBannerForTech(Topics.CLOJURE, topicSub!!)

            Topics.CPP -> addBannerForTech(Topics.CPP, topicSub!!)

            Topics.CSS -> addBannerForTech(Topics.CSS, topicSub!!)

            Topics.DATA -> addBannerForTech(Topics.DATA, topicSub!!)

            Topics.DEVOPS -> addBannerForTech(Topics.DEVOPS, topicSub!!)

            Topics.DOTNET -> addBannerForTech(Topics.DOTNET, topicSub!!)

            Topics.ELIXIR -> addBannerForTech(Topics.ELIXIR, topicSub!!)

            Topics.ELM -> addBannerForTech(Topics.ELM, topicSub!!)

            Topics.GENERAL -> addBannerForTech(Topics.GENERAL, topicSub!!)

            Topics.GOLANG -> addBannerForTech(Topics.GOLANG, topicSub!!)

            Topics.GRAPHQL -> addBannerForTech(Topics.GRAPHQL, topicSub!!)

            Topics.GROOVY -> addBannerForTech(Topics.GROOVY, topicSub!!)

            Topics.IOS -> addBannerForTech(Topics.IOS, topicSub!!)

            Topics.IOT -> addBannerForTech(Topics.IOT, topicSub!!)

            Topics.JAVA -> addBannerForTech(Topics.JAVA, topicSub!!)

            Topics.JAVASCRIPT -> addBannerForTech(Topics.JAVASCRIPT, topicSub!!)

            Topics.KOTLIN -> addBannerForTech(Topics.KOTLIN, topicSub!!)

            Topics.LEADERSHIP -> addBannerForTech(Topics.LEADERSHIP, topicSub!!)

            Topics.NETWORKING -> addBannerForTech(Topics.NETWORKING, topicSub!!)

            Topics.PHP -> addBannerForTech(Topics.PHP, topicSub!!)

            Topics.PRODUCT -> addBannerForTech(Topics.PRODUCT, topicSub!!)

            Topics.PYTHON -> addBannerForTech(Topics.PYTHON, topicSub!!)

            Topics.RUBY -> addBannerForTech(Topics.RUBY, topicSub!!)

            Topics.RUST -> addBannerForTech(Topics.RUST, topicSub!!)

            Topics.SCALA -> addBannerForTech(Topics.SCALA, topicSub!!)

            Topics.SECURITY -> addBannerForTech(Topics.SECURITY, topicSub!!)

            Topics.TECH_COMM -> addBannerForTech(Topics.TECH_COMM, topicSub!!)

            Topics.TYPESCRIPT -> addBannerForTech(Topics.TYPESCRIPT, topicSub!!)

            Topics.UX -> addBannerForTech(Topics.UX, topicSub!!)
        }
    }

    private fun addBannerForTech(tech: String, info: String) {
        val inflater = LayoutInflater.from(this)
        val view =
            inflater.inflate(R.layout.layout_generic_banner, headerContainer, false)
        view.tvGenericSub.text =
            String.format(getString(R.string.tech_conf_sub), info)
        view.tvGenericTitle.text = tech
        ImageUtils.loadImageDrawable(this, ImageUtils.getTopicDrawableResId(tech), view.ivTech)
        headerContainer.addView(view)
    }

    private fun setupRecyclerView() {
        rvEvents.apply {
            adapter = eventsItemAdapter
            layoutManager = if (screenUtils.isPortrait()) {
                linearLayoutManager
            } else {

                gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (eventsItemAdapter.getItemViewType(position)) {
                            EventsItemAdapter.HEADER -> 2
                            else -> 1
                        }
                    }
                }
                gridLayoutManager
            }
        }
    }

    private fun setupErrorLayouts() {
        btnErrorRetry.setOnClickListener {
            val topicsList = intent.getStringArrayListExtra(TOPICS_LIST)
            viewModel.getUpComingEventsForTech(topicsList!!, true)
        }

        btnNoConnectionRetry.setOnClickListener {
            val topicsList = intent.getStringArrayListExtra(TOPICS_LIST)
            viewModel.getUpComingEventsForTech(topicsList!!, true)
        }
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshList.setOnRefreshListener {
            val topicsList = intent.getStringArrayListExtra(TOPICS_LIST)
            val isArchive = intent.getBooleanExtra(IS_ARCHIVE, false)
            if (!isArchive) {
                viewModel.getUpComingEventsForTech(topicsList!!, true)
            } else {
                val year = intent.getIntExtra(ARCHIVE_YEAR, -1)
                viewModel.getEventsForYearAndTech(year, topicsList!![0], true)
            }
        }
    }

    private fun setupListEmptyLayout() {

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@EventsListActivity, ArchiveActivity::class.java))
            }
        }

        val sb = StringBuilder()
        sb.apply {
            append(getString(R.string.sorry_no_conferences))
            append("\n")
            append(getString(R.string.check_archive))
        }

        val spannable = SpannableString(sb.toString())
        spannable.setSpan(clickableSpan, 56, 64, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

        layoutListIsEmpty.tvListIsEmpty.apply {
            requestFocus()
            text = spannable
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }
    }
}
