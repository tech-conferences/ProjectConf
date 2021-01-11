/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.bookmarks

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.FragmentComponent
import io.rot.labs.projectconf.ui.base.BaseFragment
import io.rot.labs.projectconf.ui.eventsItem.EventsItemAdapter
import io.rot.labs.projectconf.utils.display.ScreenResourcesHelper
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_bookmarks.btnFilterBookmarks
import kotlinx.android.synthetic.main.fragment_bookmarks.layoutListIsEmptyBookmarks
import kotlinx.android.synthetic.main.fragment_bookmarks.rvBookmarks
import kotlinx.android.synthetic.main.layout_list_is_empty.view.tvListIsEmpty

class BookmarksFragment : BaseFragment<BookmarksViewModel>() {

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var gridLayoutManager: GridLayoutManager

    @Inject
    lateinit var screenUtils: ScreenResourcesHelper

    @Inject
    lateinit var eventsItemAdapter: EventsItemAdapter

    private var isCFPFilter = false

    companion object {
        const val TAG = "BookmarksFragment"
        fun newInstance(): BookmarksFragment {
            val args = Bundle()
            val fragment = BookmarksFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun injectDependencies(buildComponent: FragmentComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        setupRecyclerView()

        btnFilterBookmarks.setOnClickListener {
            showFilterMenu(it)
        }
    }

    override fun provideLayoutId(): Int = R.layout.fragment_bookmarks

    override fun setupObservables() {
        super.setupObservables()

        viewModel.isCFPFilter.observe(this, Observer {
            isCFPFilter = it

            if (it) {
                btnFilterBookmarks.text = getString(R.string.bookmarks_cfp)
                viewModel.getCFPReminderEvents()
            } else {
                btnFilterBookmarks.text = getString(R.string.bookmarks_all)
                viewModel.getAllBookmarks()
            }
        })

        viewModel.bookmarkedEvents.observe(this, Observer {
            eventsItemAdapter.updateData(it)
            if (it.isEmpty()) {
                handleListIsEmptyLayout()
            } else {
                layoutListIsEmptyBookmarks.isVisible = false
                rvBookmarks.isVisible = true
            }
        })

        viewModel.cfpReminders.observe(this, Observer {
            eventsItemAdapter.updateData(it)
            if (it.isEmpty()) {
                handleListIsEmptyLayout()
            } else {
                layoutListIsEmptyBookmarks.isVisible = false
                rvBookmarks.isVisible = true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (isCFPFilter) {
            viewModel.getCFPReminderEvents()
        } else {
            viewModel.getAllBookmarks()
        }
    }

    private fun handleListIsEmptyLayout() {
        rvBookmarks.isVisible = false
        layoutListIsEmptyBookmarks.isVisible = true
        if (isCFPFilter) {
            layoutListIsEmptyBookmarks.tvListIsEmpty.text = getString(R.string.no_cfp_text)
        } else {
            layoutListIsEmptyBookmarks.tvListIsEmpty.text = getString(R.string.no_bookmarks_text)
        }
    }

    private fun setupRecyclerView() {
        rvBookmarks.apply {
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

    private fun showFilterMenu(anchor: View) {
        val popup = PopupMenu(requireContext(), anchor)
        popup.menuInflater.inflate(R.menu.bookmarks_filter_menu, popup.menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.allBookmarks -> {
                    viewModel.onCFPFilterChange(false)
                    true
                }
                R.id.cfpReminders -> {
                    viewModel.onCFPFilterChange(true)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}
