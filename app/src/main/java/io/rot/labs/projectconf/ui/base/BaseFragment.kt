package io.rot.labs.projectconf.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.rot.labs.projectconf.ConfApplication
import io.rot.labs.projectconf.di.component.DaggerFragmentComponent
import io.rot.labs.projectconf.di.component.FragmentComponent
import io.rot.labs.projectconf.di.module.FragmentModule
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    @Inject
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(getBuildComponent())
        super.onCreate(savedInstanceState)
        setupObservables()
        viewModel.onCreate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(provideLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(savedInstanceState)
    }

    protected open fun setupObservables() {
        viewModel.messageStringId.observe(this, Observer {
            it.data?.let { showMessageToast(it) }
        })
        viewModel.messageString.observe(this, Observer {
            it.data?.let { showMessageToast(it) }
        })
    }

    fun showMessageToast(message: String) =
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()

    fun showMessageToast(@StringRes resId: Int) = showMessageToast(getString(resId))

    fun goBack() {
        if (activity is BaseActivity<*>) (activity as BaseActivity<*>).goBack()
    }

    private fun getBuildComponent() = DaggerFragmentComponent
        .builder()
        .applicationComponent((context!!.applicationContext as ConfApplication).appComponent)
        .fragmentModule(FragmentModule(this))
        .build()

    abstract fun injectDependencies(buildComponent: FragmentComponent)

    abstract fun setupView(savedInstanceState: Bundle?)

    @LayoutRes
    abstract fun provideLayoutId(): Int
}
