package io.rot.labs.projectconf.ui.allTech

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.rot.labs.projectconf.ui.base.BaseViewModel
import io.rot.labs.projectconf.utils.common.Topics
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
                2014, 2015, 2016 -> {
                    val topicsList = listOf(
                        Topics.JAVASCRIPT,
                        Topics.RUBY,
                        Topics.UX
                    )
                    topicSet.addAll(topicsList)
                }
                2017 -> {
                    val topicsList = listOf(
                        Topics.ANDROID,
                        Topics.CSS,
                        Topics.DEVOPS,
                        Topics.GENERAL,
                        Topics.IOS,
                        Topics.JAVASCRIPT,
                        Topics.PHP,
                        Topics.RUBY,
                        Topics.TECH_COMM,
                        Topics.UX
                    )
                    topicSet.addAll(topicsList)
                }
                2018 -> {
                    val topicsList = listOf(
                        Topics.ANDROID,
                        Topics.CSS,
                        Topics.DATA,
                        Topics.DEVOPS,
                        Topics.DOTNET,
                        Topics.ELIXIR,
                        Topics.GENERAL,
                        Topics.GOLANG,
                        Topics.GRAPHQL,
                        Topics.IOS,
                        Topics.JAVASCRIPT,
                        Topics.PHP,
                        Topics.PYTHON,
                        Topics.RUBY,
                        Topics.RUST,
                        Topics.SCALA,
                        Topics.SECURITY,
                        Topics.TECH_COMM,
                        Topics.UX
                    )
                    topicSet.addAll(topicsList)
                }
                2019 -> {
                    val topicsList = listOf(
                        Topics.ANDROID,
                        Topics.CLOJURE,
                        Topics.CPP,
                        Topics.CSS,
                        Topics.DATA,
                        Topics.DEVOPS,
                        Topics.DOTNET,
                        Topics.ELIXIR,
                        Topics.ELM,
                        Topics.GENERAL,
                        Topics.GOLANG,
                        Topics.GRAPHQL,
                        Topics.GROOVY,
                        Topics.IOS,
                        Topics.JAVA,
                        Topics.JAVASCRIPT,
                        Topics.LEADERSHIP,
                        Topics.NETWORKING,
                        Topics.PHP,
                        Topics.PRODUCT,
                        Topics.PYTHON,
                        Topics.RUBY,
                        Topics.RUST,
                        Topics.SCALA,
                        Topics.SECURITY,
                        Topics.TECH_COMM,
                        Topics.TYPESCRIPT,
                        Topics.UX
                    )
                    topicSet.addAll(topicsList)
                }
                2020 -> {
                    val topicsList = listOf(
                        Topics.ANDROID,
                        Topics.CLOJURE,
                        Topics.CPP,
                        Topics.CSS,
                        Topics.DATA,
                        Topics.DEVOPS,
                        Topics.DOTNET,
                        Topics.ELIXIR,
                        Topics.ELM,
                        Topics.GENERAL,
                        Topics.GOLANG,
                        Topics.GRAPHQL,
                        Topics.IOS,
                        Topics.IOT,
                        Topics.JAVA,
                        Topics.JAVASCRIPT,
                        Topics.KOTLIN,
                        Topics.LEADERSHIP,
                        Topics.NETWORKING,
                        Topics.PHP,
                        Topics.PRODUCT,
                        Topics.PYTHON,
                        Topics.RUBY,
                        Topics.RUST,
                        Topics.SCALA,
                        Topics.SECURITY,
                        Topics.TECH_COMM,
                        Topics.TYPESCRIPT,
                        Topics.UX
                    )
                    topicSet.addAll(topicsList)
                }
                2021 -> {
                    val topicsList = listOf(
                        Topics.ANDROID,
                        Topics.CLOJURE,
                        Topics.CSS,
                        Topics.DATA,
                        Topics.DEVOPS,
                        Topics.DOTNET,
                        Topics.ELIXIR,
                        Topics.GENERAL,
                        Topics.GOLANG,
                        Topics.GRAPHQL,
                        Topics.IOS,
                        Topics.JAVA,
                        Topics.JAVASCRIPT,
                        Topics.PHP,
                        Topics.PRODUCT,
                        Topics.PYTHON,
                        Topics.RUBY,
                        Topics.RUST,
                        Topics.SCALA,
                        Topics.SECURITY,
                        Topics.TECH_COMM,
                        Topics.TYPESCRIPT,
                        Topics.UX
                    )
                    topicSet.addAll(topicsList)
                }
            }
        }
        allTechTopics.postValue(topicSet.toList().map {
            if (isArchive) {
                Pair(it, yearList[0])
            } else {
                Pair(it, null)
            }
        }.sortedBy {
            it.first
        })
    }
}
