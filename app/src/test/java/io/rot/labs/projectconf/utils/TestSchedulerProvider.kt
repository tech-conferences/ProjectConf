package io.rot.labs.projectconf.utils

import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class TestSchedulerProvider(private val testScheduler: TestScheduler) : SchedulerProvider {

    override fun computation(): Scheduler = testScheduler

    override fun io(): Scheduler = testScheduler

    override fun ui(): Scheduler = testScheduler

}