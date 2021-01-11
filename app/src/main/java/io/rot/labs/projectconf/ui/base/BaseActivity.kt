/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.base

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import io.rot.labs.projectconf.ConfApplication
import io.rot.labs.projectconf.di.component.ActivityComponent
import io.rot.labs.projectconf.di.component.DaggerActivityComponent
import io.rot.labs.projectconf.di.module.ActivityModule
import io.rot.labs.projectconf.utils.display.ScreenResourcesHelper
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    @Inject
    lateinit var viewModel: VM

    @Inject
    lateinit var screenUtils: ScreenResourcesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(getBuildComponent())
        super.onCreate(savedInstanceState)
        setContentView(provideLayoutId())
        screenUtils.setStatusBarColor(this)
        setupObservables()
        setupView(savedInstanceState)
        viewModel.onCreate()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finishAfterTransition()
        }
        return super.onOptionsItemSelected(item)
    }

    open fun goBack() = onBackPressed()

    private fun getBuildComponent() = DaggerActivityComponent
        .builder()
        .applicationComponent((application as ConfApplication).appComponent)
        .activityModule(ActivityModule(this))
        .build()

    abstract fun injectDependencies(buildComponent: ActivityComponent)

    @LayoutRes
    abstract fun provideLayoutId(): Int

    abstract fun setupView(savedInstanceState: Bundle?)

    protected open fun setupObservables() {
        viewModel.messageStringId.observe(this, Observer {
            it.data?.let { showMessageToast(it) }
        })
        viewModel.messageString.observe(this, Observer {
            it.data?.let { showMessageToast(it) }
        })
    }

    fun showMessageToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    fun showMessageToast(@StringRes resId: Int) =
        showMessageToast(getString(resId))
}
