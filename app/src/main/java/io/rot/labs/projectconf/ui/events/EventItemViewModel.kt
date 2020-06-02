package io.rot.labs.projectconf.ui.events

import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.data.model.Event
import io.rot.labs.projectconf.ui.base.BaseItemViewModel
import io.rot.labs.projectconf.utils.network.NetworkHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider
import javax.inject.Inject

class EventItemViewModel @Inject constructor(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
):BaseItemViewModel<Event>(schedulerProvider, compositeDisposable, networkHelper) {


    override fun onCreate() {

    }


}