package io.rot.labs.projectconf.ui.upcoming

import android.os.Bundle
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.FragmentComponent
import io.rot.labs.projectconf.ui.base.BaseFragment

class UpComingFragment : BaseFragment<UpComingViewModel>() {

    companion object {
        const val TAG = "UpComingFragment"
        fun newInstance(): UpComingFragment {
            val args = Bundle()
            val fragment = UpComingFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun injectDependencies(buildComponent: FragmentComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {

    }

    override fun provideLayoutId(): Int = R.layout.fragment_upcoming


}