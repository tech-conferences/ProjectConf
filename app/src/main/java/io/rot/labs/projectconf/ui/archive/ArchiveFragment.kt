package io.rot.labs.projectconf.ui.archive

import android.os.Bundle
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.FragmentComponent
import io.rot.labs.projectconf.ui.base.BaseFragment

class ArchiveFragment : BaseFragment<ArchiveViewModel>() {

    companion object {
        const val TAG = "ArchiveFragment"

        fun newInstance(): ArchiveFragment {
            val args = Bundle()
            val fragment = ArchiveFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun injectDependencies(buildComponent: FragmentComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
    }

    override fun provideLayoutId(): Int = R.layout.fragment_archive

}