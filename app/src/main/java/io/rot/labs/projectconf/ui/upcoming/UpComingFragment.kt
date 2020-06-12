package io.rot.labs.projectconf.ui.upcoming

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.FragmentComponent
import io.rot.labs.projectconf.ui.base.BaseFragment
import io.rot.labs.projectconf.ui.eventsItem.EventsItemAdapter
import io.rot.labs.projectconf.ui.eventsItem.EventsItemHelper
import io.rot.labs.projectconf.ui.upcoming.banner.BannerViewModel
import io.rot.labs.projectconf.ui.upcoming.banner.TechBannerAdapter
import io.rot.labs.projectconf.ui.upcoming.banner.ZoomOutPageTransformer
import io.rot.labs.projectconf.utils.common.Resource
import io.rot.labs.projectconf.utils.common.Toaster
import io.rot.labs.projectconf.utils.common.Topics
import io.rot.labs.projectconf.utils.display.ScreenResourcesHelper
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.android.synthetic.main.fragment_upcoming.*
import kotlinx.android.synthetic.main.layout_distinct_languages.*
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.layout_no_connection.*
import kotlinx.android.synthetic.main.layout_upcoming_loading.view.*

class UpComingFragment : BaseFragment<UpComingViewModel>() {

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var gridLayoutManager: GridLayoutManager

    @Inject
    lateinit var eventsItemAdapter: EventsItemAdapter

    @Inject
    lateinit var techBannerAdapter: TechBannerAdapter

    @Inject
    lateinit var zoomOutPageTransformer: ZoomOutPageTransformer

    @Inject
    lateinit var bannerViewModel: BannerViewModel

    @Inject
    lateinit var screenUtils: ScreenResourcesHelper

    companion object {
        const val TAG = "UpComingFragment"
        fun newInstance(): UpComingFragment {
            val args = Bundle()
            val fragment = UpComingFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        bannerViewModel.startAutoIterator()
    }

    override fun onStop() {
        super.onStop()
        bannerViewModel.cancelAutoIterator()
    }

    override fun provideLayoutId(): Int = R.layout.fragment_upcoming

    override fun injectDependencies(buildComponent: FragmentComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        setUpBanner()
        setUpDistinctLanguages()
        setupRecyclerView()
        setupErrorLayouts()
        setupSwipeRefreshLayout()
    }

    override fun setupObservables() {

        viewModel.messageStringId.observe(this, Observer {
            it.data?.let { data ->
                if (data == R.string.network_internet_not_connected || data == R.string.network_could_not_connect) {
                    if (viewModel.upcomingEvents.value?.isNotEmpty() == true) {
                        Toaster.show(context, getString(R.string.could_not_refresh))
                    } else {
                        layoutNoConnection.isVisible = true
                        layoutError.isVisible = false
                        upComingEventsContainer.isVisible = false
                        shimmerUpComing.isVisible = false
                        aviLoader.isVisible = false
                        tvNoInternet.text = getString(it.data)
                    }
                } else {
                    if (viewModel.upcomingEvents.value?.isNotEmpty() == true) {
                        Toast.makeText(context, "Could not refresh", Toast.LENGTH_LONG).show()
                    } else {
                        layoutError.isVisible = true
                        layoutNoConnection.isVisible = false
                        upComingEventsContainer.isVisible = false
                        shimmerUpComing.isVisible = false
                        aviLoader.isVisible = false
                        tvFatalError.text = getString(data)
                    }
                }
                viewModel.messageStringId.postValue(Resource.error(null))
            }
        })

        viewModel.messageString.observe(this, Observer {
            it.data?.let { data ->
                if (viewModel.upcomingEvents.value?.isNotEmpty() == true) {
                    Toaster.show(context, getString(R.string.could_not_refresh))
                } else {
                    layoutError.isVisible = true
                    layoutNoConnection.isVisible = false
                    upComingEventsContainer.isVisible = false
                    shimmerUpComing.isVisible = false
                    aviLoader.isVisible = false

                    tvFatalError.text = data
                }
                viewModel.messageString.postValue(Resource.error(null))
            }
        })

        viewModel.upcomingEvents.observe(this, Observer {
            eventsItemAdapter.updateData(it)
        })

        bannerViewModel.bannerPosition.observe(this, Observer {
            techBannerPager.setCurrentItem(it, true)
        })

        viewModel.progress.observe(this, Observer {
            if (it) {
                upComingEventsContainer.isVisible = false
                layoutError.isVisible = false
                layoutNoConnection.isVisible = false
                shimmerUpComing.apply {
                    isVisible = true
                    tvQuote.text = getRandomQuote()
                    startShimmer()
                }
                aviLoader.apply {
                    isVisible = true
                    smoothToShow()
                }
            } else {
                upComingEventsContainer.isVisible = true
                layoutError.isVisible = false
                layoutNoConnection.isVisible = false
                shimmerUpComing.apply {
                    isVisible = false
                    stopShimmer()
                }
                aviLoader.apply {
                    isVisible = false
                    smoothToHide()
                }
            }
        })
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

    private fun setUpBanner() {
        val slidingDots = Array(TechBannerAdapter.NUM_BANNERS) { ImageView(context) }

        for (i in slidingDots.indices) {
            slidingDots[i].setImageDrawable(
                ContextCompat.getDrawable(
                    context!!,
                    R.drawable.non_active_indicator
                )
            )
            val params =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            params.setMargins(8, 0, 8, 0)
            sliderDotsContainer.addView(slidingDots[i], params)
        }

        slidingDots[bannerViewModel.iterator % TechBannerAdapter.NUM_BANNERS].setImageDrawable(
            ContextCompat.getDrawable(
                context!!,
                R.drawable.active_indicator
            )
        )

        techBannerPager.apply {
            adapter = techBannerAdapter
            setPageTransformer(zoomOutPageTransformer)

            currentItem = bannerViewModel.iterator

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    bannerViewModel.iterator = position

                    for (i in slidingDots.indices) {
                        slidingDots[i].setImageDrawable(
                            ContextCompat.getDrawable(
                                context!!,
                                R.drawable.non_active_indicator
                            )
                        )
                    }
                    slidingDots[position % TechBannerAdapter.NUM_BANNERS].setImageDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.active_indicator
                        )
                    )
                }
            })
        }
    }

    private fun setUpDistinctLanguages() {

        ivPython.setOnClickListener {
            EventsItemHelper.navigateToEventsListActivity(
                context!!,
                Topics.PYTHON,
                "${Topics.PYTHON} devs",
                arrayListOf(Topics.PYTHON)
            )
        }

        ivGolang.setOnClickListener {
            EventsItemHelper.navigateToEventsListActivity(
                context!!,
                Topics.GOLANG,
                "${Topics.GOLANG} devs",
                arrayListOf(Topics.GOLANG)
            )
        }

        ivRust.setOnClickListener {
            EventsItemHelper.navigateToEventsListActivity(
                context!!,
                Topics.RUST,
                "${Topics.RUST} devs",
                arrayListOf(Topics.RUST)
            )
        }

        ivGraphql.setOnClickListener {
            EventsItemHelper.navigateToEventsListActivity(
                context!!,
                Topics.GRAPHQL,
                "${Topics.GRAPHQL} devs",
                arrayListOf(Topics.GRAPHQL)
            )
        }

        ivShowAll.setOnClickListener {
            // show tech categoryies activitity
        }
    }

    private fun setupErrorLayouts() {
        btnErrorRetry.setOnClickListener {
            viewModel.getUpComingEventsForCurrentMonth(true)
        }

        btnNoConnectionRetry.setOnClickListener {
            viewModel.getUpComingEventsForCurrentMonth(true)
        }
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getUpComingEventsForCurrentMonth(true)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun getRandomQuote(): String {
        val quotes = resources.getStringArray(R.array.quotes)
        val i = Random.nextInt(0, quotes.size)
        return quotes[i]
    }
}
