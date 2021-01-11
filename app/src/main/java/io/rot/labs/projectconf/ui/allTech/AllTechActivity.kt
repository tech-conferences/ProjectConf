/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.allTech

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.ActivityComponent
import io.rot.labs.projectconf.ui.base.BaseActivity
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_alltech.matToolbarAllTech
import kotlinx.android.synthetic.main.activity_alltech.rvAllTech

class AllTechActivity : BaseActivity<AllTechViewModel>() {

    @Inject
    lateinit var gridLayoutManager: GridLayoutManager

    @Inject
    lateinit var allTechAdapter: AllTechAdapter

    companion object {
        const val YEARS = "years"
        const val IS_ARCHIVE = "is_archive"
    }

    override fun injectDependencies(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun provideLayoutId(): Int = R.layout.activity_alltech

    override fun setupView(savedInstanceState: Bundle?) {

        rvAllTech.apply {
            layoutManager = if (screenUtils.isPortrait()) {
                gridLayoutManager.apply { spanCount = 2 }
            } else {
                gridLayoutManager.apply { spanCount = 3 }
            }

            adapter = allTechAdapter
        }

        val years = intent.getIntegerArrayListExtra(YEARS)
        val isArchive = intent.getBooleanExtra(IS_ARCHIVE, false)
        viewModel.getTopicsListForYear(years!!, isArchive)

        setSupportActionBar(matToolbarAllTech)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        supportActionBar?.title = " "

        if (isArchive) {
            supportActionBar?.title = years[0].toString()
        }
    }

    override fun setupObservables() {
        super.setupObservables()
        viewModel.allTechTopics.observe(this, Observer {
            allTechAdapter.updateData(it)
        })
    }
}
