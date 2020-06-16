package io.rot.labs.projectconf.ui.allTech

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.ui.base.BaseItemViewModel
import io.rot.labs.projectconf.utils.display.ImageUtils
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider
import javax.inject.Inject

class AllTechItemViewModel @Inject constructor(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkDBHelper: NetworkDBHelper
) : BaseItemViewModel<Pair<String, Int?>>(schedulerProvider, compositeDisposable, networkDBHelper) {

    val techResId: LiveData<Int> = Transformations.map(data) {
        ImageUtils.getTopicDrawableResId(it.first)
    }

    val tech = Transformations.map(data) {
        it.first
    }

    val year = Transformations.map(data) {
        it.second
    }

    override fun onCreate() {}
}
