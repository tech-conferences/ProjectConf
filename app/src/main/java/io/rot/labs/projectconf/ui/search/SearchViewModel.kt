package io.rot.labs.projectconf.ui.search

import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.utils.network.NetworkHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class SearchViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {
    override fun onCreate() {

    }


}