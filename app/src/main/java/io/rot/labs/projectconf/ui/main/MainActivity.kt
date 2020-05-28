package io.rot.labs.projectconf.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.rot.labs.projectconf.ConfApplication
import io.rot.labs.projectconf.R
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.di.component.DaggerActivityComponent
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.rx.RxSchedulerProvider
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var eventsRepository: EventsRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        eventsRepository.getEventsOfYear(TimeDateUtils.getConfYearsList().last() - 8)
            .subscribeOn(RxSchedulerProvider().io())
            .subscribe(
                {
                    Log.d("PUI","list size ${it.size} ")
                },
                {
                    if( it is com.jakewharton.retrofit2.adapter.rxjava2.HttpException){
                        Log.d("PUI", "code ${it.code()}")
                    }
                    Log.d("PUI", "Error $it")
                },
                {
                    Log.d("PUI","onComplete")
                }
            )
    }

    fun injectDependencies() {
        val component = DaggerActivityComponent
            .builder()
            .applicationComponent((application as ConfApplication).appComponent)
            .build()
        component.inject(this)
    }
}