package io.rot.labs.projectconf.ui.upcoming

import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.repository.EventsRepository
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.utils.network.NetworkHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class UpComingViewModel(
    private val schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val eventsRepository: EventsRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    override fun onCreate() {

    }
}