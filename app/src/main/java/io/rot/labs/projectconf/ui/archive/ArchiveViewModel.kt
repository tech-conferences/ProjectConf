package io.rot.labs.projectconf.ui.archive

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class ArchiveViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkDBHelper: NetworkDBHelper
) : BaseViewModel(schedulerProvider, compositeDisposable, networkDBHelper) {

    val archiveYears = MutableLiveData<List<Int>>()

    override fun onCreate() {
        getArchiveYears()
    }

    fun getArchiveYears() {
        val yearList = TimeDateUtils.getConfYearsList()
        val archive = yearList.subList(0, yearList.lastIndex - 1).reversed()
        archiveYears.postValue(archive)
    }
}
