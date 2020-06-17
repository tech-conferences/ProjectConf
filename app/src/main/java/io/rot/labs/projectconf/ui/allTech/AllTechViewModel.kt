package io.rot.labs.projectconf.ui.allTech

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.utils.common.TopicsList
import io.rot.labs.projectconf.utils.network.NetworkDBHelper
import io.rot.labs.projectconf.utils.rx.SchedulerProvider

class AllTechViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkDBHelper: NetworkDBHelper
) : BaseViewModel(schedulerProvider, compositeDisposable, networkDBHelper) {

    val allTechTopics = MutableLiveData<List<Pair<String, Int?>>>()

    override fun onCreate() {}

    fun getTopicsListForYear(yearList: List<Int>, isArchive: Boolean) {
        if (allTechTopics.value?.isNotEmpty() == true) {
            return
        }
        val topicSet = HashSet<String>()
        for (year in yearList) {
            when (year) {
                2014, 2015, 2016 -> topicSet.addAll(TopicsList.YEAR_2014_2015_2016)

                2017 -> topicSet.addAll(TopicsList.YEAR_2017)

                2018 -> topicSet.addAll(TopicsList.YEAR_2018)

                2019 -> topicSet.addAll(TopicsList.YEAR_2019)

                2020 -> topicSet.addAll(TopicsList.YEAR_2020)

                2021 -> topicSet.addAll(TopicsList.YEAR_2021)
            }
        }
        allTechTopics.postValue(mapToPairedYearList(topicSet.toList(), isArchive, yearList[0]))
    }

    fun mapToPairedYearList(list: List<String>, isArchive: Boolean, year: Int) =
        list.map {
            if (isArchive) {
                Pair(it, year)
            } else {
                Pair(it, null)
            }
        }.sortedBy {
            it.first
        }
}
