package io.rot.labs.projectconf.ui.twitter

import android.os.Bundle
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.FragmentComponent
import io.rot.labs.projectconf.ui.base.BaseFragment

class TwitterFragment : BaseFragment<TwitterViewModel>() {

    companion object {
        const val TAG = "TwitterFragment"

        fun newInstance(): TwitterFragment {
            val args = Bundle()
            val fragment = TwitterFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun injectDependencies(buildComponent: FragmentComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
    }

    override fun provideLayoutId(): Int = R.layout.fragment_twitter
}
