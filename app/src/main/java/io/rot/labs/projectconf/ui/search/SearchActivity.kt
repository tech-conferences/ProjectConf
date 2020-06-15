package io.rot.labs.projectconf.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.ActivityComponent
import io.rot.labs.projectconf.ui.base.BaseActivity
import io.rot.labs.projectconf.utils.common.Toaster
import io.rot.labs.projectconf.utils.display.ScreenResourcesHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider
import io.rot.labs.projectconf.utils.search.getTextChangeObservable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_search.aviLoaderSearch
import kotlinx.android.synthetic.main.activity_search.etSearch
import kotlinx.android.synthetic.main.activity_search.ivBackSearch
import kotlinx.android.synthetic.main.activity_search.ivClearSearch
import kotlinx.android.synthetic.main.activity_search.rvSearchResults

class SearchActivity : BaseActivity<SearchViewModel>() {

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var gridLayoutManager: GridLayoutManager

    @Inject
    lateinit var searchAdapter: SearchAdapter

    @Inject
    lateinit var screenResourcesHelper: ScreenResourcesHelper

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    companion object {
        const val YEAR_LIST = "year_list"
    }

    override fun injectDependencies(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun provideLayoutId(): Int = R.layout.activity_search

    override fun setupView(savedInstanceState: Bundle?) {

        ivBackSearch.setOnClickListener {
            finishAfterTransition()
        }

        ivClearSearch.setOnClickListener {
            etSearch.setText("", TextView.BufferType.EDITABLE)
        }

        rvSearchResults.apply {
            adapter = searchAdapter
            layoutManager = if (screenResourcesHelper.isPortrait()) {
                linearLayoutManager
            } else {
                gridLayoutManager
            }
        }

        val yearsList = intent.getIntegerArrayListExtra(YEAR_LIST)

        etSearch.setOnFocusChangeListener { _, hasFocus ->
            Log.d("PUI", "edittext focus $hasFocus")
            if (hasFocus) {
                compositeDisposable.add(
                    etSearch.getTextChangeObservable()
                        .debounce(300, TimeUnit.MILLISECONDS)
                        .filter {
                            return@filter !(it.isNotEmpty() && it.length < 3)
                        }
                        .distinctUntilChanged()
                        .switchMap {
                            viewModel.getRecentEventsByQuery(it, yearsList!!)
                        }
                        .subscribeOn(schedulerProvider.ui())
                        .observeOn(schedulerProvider.ui())
                        .subscribe(
                            {
                                if (it.isEmpty()) {
                                    searchAdapter.updateData(emptyList())
                                }
                            },
                            {
                                Toaster.show(this, "Oops! Sorry, something wrong happened")
                            }, {
                                hideSoftKeyboard()
                                etSearch.clearFocus()
                            }
                        )
                )
            }
        }
    }

    override fun setupObservables() {
        super.setupObservables()

        viewModel.events.observe(this, Observer {
            searchAdapter.updateData(it)
        })

        viewModel.nameQueryHolder.observe(this, Observer {
            if (it.isEmpty()) {
                ivClearSearch.visibility = View.INVISIBLE
            } else {
                ivClearSearch.visibility = View.VISIBLE
            }
        })

        viewModel.progress.observe(this, Observer {
            if (it) {
                rvSearchResults.isVisible = false
                aviLoaderSearch.apply {
                    isVisible = true
                    smoothToShow()
                }
            } else {
                rvSearchResults.isVisible = true
                aviLoaderSearch.apply {
                    smoothToHide()
                    isVisible = false
                }
            }
        })
    }

    private fun hideSoftKeyboard() {
        val view = this.currentFocus
        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
