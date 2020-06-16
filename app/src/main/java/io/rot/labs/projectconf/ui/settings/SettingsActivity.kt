package io.rot.labs.projectconf.ui.settings

import android.os.Bundle
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.ActivityComponent
import io.rot.labs.projectconf.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.matToolBarSettings

class SettingsActivity : BaseActivity<SettingsViewModel>() {
    override fun injectDependencies(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun provideLayoutId(): Int = R.layout.activity_settings

    override fun setupView(savedInstanceState: Bundle?) {
        setSupportActionBar(matToolBarSettings)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }
}
