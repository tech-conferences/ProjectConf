package io.rot.labs.projectconf.di.module

import androidx.fragment.app.Fragment
import dagger.Module
import io.rot.labs.projectconf.ui.base.BaseFragment

@Module
class FragmentModule(private val fragment : BaseFragment<*>) {
}