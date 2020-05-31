package io.rot.labs.projectconf.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.rot.labs.projectconf.ConfApplication
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.data.local.db.DatabaseService
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.di.component.ActivityComponent
import io.rot.labs.projectconf.di.component.DaggerActivityComponent
import io.rot.labs.projectconf.ui.base.BaseActivity
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.rx.RxSchedulerProvider
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel>() {

    override fun injectDependencies(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun setupView(savedInstanceState: Bundle?) {

    }

}