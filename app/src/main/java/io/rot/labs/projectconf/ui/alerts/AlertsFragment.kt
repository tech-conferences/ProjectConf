package io.rot.labs.projectconf.ui.alerts

import android.os.Bundle
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.FragmentComponent
import io.rot.labs.projectconf.ui.base.BaseFragment

class AlertsFragment : BaseFragment<AlertsViewModel>() {

    companion object {
        const val TAG = "AlertsFragment"

        fun newInstance(): AlertsFragment {
            val args = Bundle()
            val fragment = AlertsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun injectDependencies(buildComponent: FragmentComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {

    }

    override fun provideLayoutId(): Int = R.layout.fragment_alerts

}