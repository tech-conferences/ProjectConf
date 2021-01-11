/*
 *  Created by Rohan Maity on 11/1/21 3:19 PM
 *  Copyright (c) 2021 . All rights reserved.
 *  Last modified 11/1/21 3:12 PM
 */

package io.rot.labs.projectconf.ui.changeTheme

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.data.local.prefs.ThemePreferences
import io.rot.labs.projectconf.di.component.FragmentComponent
import io.rot.labs.projectconf.ui.base.BaseBottomSheetDialogFragment
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_change_theme.radBtnDark
import kotlinx.android.synthetic.main.fragment_change_theme.radBtnLight
import kotlinx.android.synthetic.main.fragment_change_theme.radBtnSystem

class ChangeThemeDialogFragment : BaseBottomSheetDialogFragment<ChangeThemeViewModel>() {

    companion object {
        const val TAG = "ChangeThemeDialogFragment"
    }

    @Inject
    lateinit var themePreferences: ThemePreferences

    private fun changeTheme(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
        themePreferences.save(mode)
    }

    override fun provideLayoutId(): Int = R.layout.fragment_change_theme

    override fun injectDependencies(buildComponent: FragmentComponent) {
        buildComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {

        when (themePreferences.getThemeMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> radBtnDark.isChecked = true
            AppCompatDelegate.MODE_NIGHT_NO -> radBtnLight.isChecked = true
            else -> radBtnSystem.isChecked = true
        }

        radBtnSystem.setOnClickListener {
            changeTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            dismiss()
        }

        radBtnDark.setOnClickListener {
            changeTheme(AppCompatDelegate.MODE_NIGHT_YES)
            dismiss()
        }

        radBtnLight.setOnClickListener {
            changeTheme(AppCompatDelegate.MODE_NIGHT_NO)
            dismiss()
        }
    }

    override fun setupObservables() {}
}
