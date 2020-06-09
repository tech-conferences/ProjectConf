package io.rot.labs.projectconf.ui.search

import android.os.Bundle
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.di.component.ActivityComponent
import io.rot.labs.projectconf.ui.base.BaseActivity

class SearchActivity : BaseActivity<SearchViewModel>() {
    override fun injectDependencies(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun provideLayoutId(): Int = R.layout.activity_search

    override fun setupView(savedInstanceState: Bundle?) {
    }
}
