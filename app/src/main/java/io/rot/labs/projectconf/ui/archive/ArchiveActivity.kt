package io.rot.labs.projectconf.ui.archive

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.ActivityComponent
import io.rot.labs.projectconf.ui.base.BaseActivity
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_archive.matToolbarArchive
import kotlinx.android.synthetic.main.activity_archive.rvArchiveYears

class ArchiveActivity : BaseActivity<ArchiveViewModel>() {

    @Inject
    lateinit var gridLayoutManager: GridLayoutManager

    @Inject
    lateinit var archiveAdapter: ArchiveAdapter

    override fun injectDependencies(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun provideLayoutId(): Int = R.layout.activity_archive

    override fun setupView(savedInstanceState: Bundle?) {
        setSupportActionBar(matToolbarArchive)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        rvArchiveYears.apply {
            adapter = archiveAdapter
            layoutManager = if (screenUtils.isPortrait()) {
                gridLayoutManager.apply { spanCount = 2 }
            } else {
                gridLayoutManager.apply { spanCount = 3 }
            }
        }
    }

    override fun setupObservables() {
        super.setupObservables()

        viewModel.archiveYears.observe(this, Observer {
            archiveAdapter.updateData(it)
        })
    }
}
