package io.rot.labs.projectconf.ui.archive

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.ViewHolderComponent
import io.rot.labs.projectconf.ui.allTech.AllTechActivity
import io.rot.labs.projectconf.ui.base.BaseItemViewHolder
import kotlinx.android.synthetic.main.item_archive_year.view.archiveYearCard
import kotlinx.android.synthetic.main.item_archive_year.view.tvArchiveYear

class ArchiveItemViewHolder(parent: ViewGroup) :
    BaseItemViewHolder<Int, ArchiveItemViewModel>(R.layout.item_archive_year, parent) {

    private var archiveYear: Int? = null

    override fun injectDependencies(buildComponent: ViewHolderComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(view: View) {
        itemView.archiveYearCard.setOnClickListener {
            archiveYear?.let {
                itemView.context.startActivity(
                    Intent(
                        itemView.context,
                        AllTechActivity::class.java
                    ).apply {
                        putExtra(AllTechActivity.YEARS, arrayListOf(it))
                        putExtra(AllTechActivity.IS_ARCHIVE, true)
                    })
            }
        }
    }

    override fun setUpObservables() {
        viewModel.data.observe(this, Observer {
            archiveYear = it
            itemView.tvArchiveYear.text = it.toString()
        })
    }
}
