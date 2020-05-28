package io.rot.labs.projectconf.data.repository

import io.reactivex.Flowable
import io.reactivex.Single
import io.rot.labs.projectconf.data.local.db.DatabaseService
import io.rot.labs.projectconf.data.model.Event
import io.rot.labs.projectconf.data.remote.NetworkService
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.common.Topics
import javax.inject.Inject

class EventsRepository @Inject constructor(
    private val databaseService: DatabaseService,
    private val networkService: NetworkService
) {

    private fun getConfSourcesList(year: Int): List<Single<List<Event>>> {
        return when (year) {
            2014 -> listOf(
                networkService.getJavaScriptEventsByYear(year),
                networkService.getRubyEventsByYear(year),
                networkService.getUXEventsByYear(year)
            )

            2015 -> listOf(
                networkService.getJavaScriptEventsByYear(year),
                networkService.getRubyEventsByYear(year),
                networkService.getUXEventsByYear(year)
            )

            2016 -> listOf(
                networkService.getJavaScriptEventsByYear(year),
                networkService.getRubyEventsByYear(year),
                networkService.getUXEventsByYear(year)
            )

            2017 -> listOf(
                networkService.getAndroidEventsByYear(year),
                networkService.getCssEventsByYear(year),
                networkService.getDataEventsByYear(year),
                networkService.getDevOpsEventsByYear(year),
                networkService.getGeneralEventsByYear(year),
                networkService.getIosEventsByYear(year),
                networkService.getJavaScriptEventsByYear(year),
                networkService.getPHPEventsByYear(year),
                networkService.getRubyEventsByYear(year),
                networkService.getTechCommEventsByYear(year),
                networkService.getUXEventsByYear(year)
            )

            2018 -> listOf(
                networkService.getAndroidEventsByYear(year),
                networkService.getCssEventsByYear(year),
                networkService.getDataEventsByYear(year),
                networkService.getDevOpsEventsByYear(year),
                networkService.getDotNetEventsByYear(year),
                networkService.getElixirEventsByYear(year),
                networkService.getGeneralEventsByYear(year),
                networkService.getGoLangEventsByYear(year),
                networkService.getGraphqlEventsByYear(year),
                networkService.getIosEventsByYear(year),
                networkService.getJavaScriptEventsByYear(year),
                networkService.getPHPEventsByYear(year),
                networkService.getPythonEventsByYear(year),
                networkService.getRubyEventsByYear(year),
                networkService.getRustEventsByYear(year),
                networkService.getScalaEventsByYear(year),
                networkService.getSecurityEventsByYear(year),
                networkService.getTechCommEventsByYear(year),
                networkService.getUXEventsByYear(year)
            )

            2019 -> listOf(
                networkService.getAndroidEventsByYear(year),
                networkService.getClojureEventsByYear(year),
                networkService.getCppEventsByYear(year),
                networkService.getCssEventsByYear(year),
                networkService.getDataEventsByYear(year),
                networkService.getDevOpsEventsByYear(year),
                networkService.getDotNetEventsByYear(year),
                networkService.getElixirEventsByYear(year),
                networkService.getElmEventsByYear(year),
                networkService.getGeneralEventsByYear(year),
                networkService.getGoLangEventsByYear(year),
                networkService.getGraphqlEventsByYear(year),
                networkService.getGroovyEventsByYear(year),
                networkService.getIosEventsByYear(year),
                networkService.getJavaEventsByYear(year),
                networkService.getJavaScriptEventsByYear(year),
                networkService.getLeadershipEventsByYear(year),
                networkService.getNetworkingEventsByYear(year),
                networkService.getPHPEventsByYear(year),
                networkService.getProductEventsByYear(year),
                networkService.getPythonEventsByYear(year),
                networkService.getRustEventsByYear(year),
                networkService.getRustEventsByYear(year),
                networkService.getScalaEventsByYear(year),
                networkService.getSecurityEventsByYear(year),
                networkService.getTechCommEventsByYear(year),
                networkService.getTypeScriptEventsByYear(year),
                networkService.getUXEventsByYear(year)
            )

            2020 -> listOf(
                networkService.getAndroidEventsByYear(year),
                networkService.getClojureEventsByYear(year),
                networkService.getCppEventsByYear(year),
                networkService.getCssEventsByYear(year),
                networkService.getDataEventsByYear(year),
                networkService.getDevOpsEventsByYear(year),
                networkService.getDotNetEventsByYear(year),
                networkService.getElixirEventsByYear(year),
                networkService.getElmEventsByYear(year),
                networkService.getGeneralEventsByYear(year),
                networkService.getGoLangEventsByYear(year),
                networkService.getGraphqlEventsByYear(year),
                networkService.getIosEventsByYear(year),
                networkService.getIotEventsByYear(year),
                networkService.getJavaEventsByYear(year),
                networkService.getJavaScriptEventsByYear(year),
                networkService.getKotlinEventsByYear(year),
                networkService.getLeadershipEventsByYear(year),
                networkService.getNetworkingEventsByYear(year),
                networkService.getPHPEventsByYear(year),
                networkService.getProductEventsByYear(year),
                networkService.getPythonEventsByYear(year),
                networkService.getRubyEventsByYear(year),
                networkService.getRustEventsByYear(year),
                networkService.getScalaEventsByYear(year),
                networkService.getSecurityEventsByYear(year),
                networkService.getTechCommEventsByYear(year),
                networkService.getTypeScriptEventsByYear(year),
                networkService.getUXEventsByYear(year)
            )

            2021 -> listOf(
                networkService.getAndroidEventsByYear(year),
                networkService.getClojureEventsByYear(year),
                networkService.getCssEventsByYear(year),
                networkService.getDataEventsByYear(year),
                networkService.getDevOpsEventsByYear(year),
                networkService.getDotNetEventsByYear(year),
                networkService.getElixirEventsByYear(year),
                networkService.getGeneralEventsByYear(year),
                networkService.getGoLangEventsByYear(year),
                networkService.getGraphqlEventsByYear(year),
                networkService.getIosEventsByYear(year),
                networkService.getJavaEventsByYear(year),
                networkService.getJavaScriptEventsByYear(year),
                networkService.getNetworkingEventsByYear(year),
                networkService.getPHPEventsByYear(year),
                networkService.getProductEventsByYear(year),
                networkService.getPythonEventsByYear(year),
                networkService.getRubyEventsByYear(year),
                networkService.getRustEventsByYear(year),
                networkService.getScalaEventsByYear(year),
                networkService.getSecurityEventsByYear(year),
                networkService.getTechCommEventsByYear(year),
                networkService.getUXEventsByYear(year)
            )

            else -> listOf(networkService.getAndroidEventsByYear(year))
        }
    }

    fun getEventsOfYear(year: Int): Flowable<List<Event>> {
        val allIn = getConfSourcesList(year)
        return Single.merge(allIn).onBackpressureBuffer()
    }

    fun getUpComingEvents(): Single<List<Event>> {
        return databaseService.eventsDao().getUpComingEventsFromCurrentYear()
    }

    fun insertEventsToLocal(list: List<Event>): Single<List<Long>> {
        return databaseService.eventsDao().insertEvents(list)
    }

    

    fun getEventsForTech(tech: String, year:Int) : Single<List<Event>>{
        return when(tech){
            Topics.ANDROID -> networkService.getAndroidEventsByYear(year)
            Topics.CLOJURE -> networkService.getClojureEventsByYear(year)
            Topics.CPP -> networkService.getCppEventsByYear(year)
            Topics.CSS -> networkService.getCssEventsByYear(year)
            Topics.DATA -> networkService.getDataEventsByYear(year)
            Topics.DEVOPS -> networkService.getDevOpsEventsByYear(year)
            Topics.DOTNET -> networkService.getDotNetEventsByYear(year)
            Topics.ELIXIR -> networkService.getElixirEventsByYear(year)
            Topics.ELM -> networkService.getElmEventsByYear(year)
            Topics.GENERAL -> networkService.getGeneralEventsByYear(year)
            Topics.GOLANG -> networkService.getGoLangEventsByYear(year)
            Topics.GRAPHQL -> networkService.getGraphqlEventsByYear(year)
            Topics.GROOVY -> networkService.getGraphqlEventsByYear(year)
            Topics.IOS -> networkService.getIosEventsByYear(year)
            Topics.IOT -> networkService.getIotEventsByYear(year)
            Topics.JAVA -> networkService.getJavaEventsByYear(year)
            Topics.JAVASCRIPT -> networkService.getJavaScriptEventsByYear(year)
            Topics.KOTLIN -> networkService.getKotlinEventsByYear(year)
            Topics.LEADERSHIP -> networkService.getLeadershipEventsByYear(year)
            Topics.NETWORKING -> networkService.getNetworkingEventsByYear(year)
            Topics.PHP -> networkService.getPHPEventsByYear(year)
            Topics.PRODUCT -> networkService.getProductEventsByYear(year)
            Topics.PYTHON -> networkService.getPythonEventsByYear(year)
            Topics.RUBY -> networkService.getRubyEventsByYear(year)
            Topics.RUST -> networkService.getRustEventsByYear(year)
            Topics.SCALA -> networkService.getScalaEventsByYear(year)
            Topics.SECURITY -> networkService.getSecurityEventsByYear(year)
            Topics.TECH_COMM -> networkService.getTechCommEventsByYear(year)
            Topics.TYPESCRIPT -> networkService.getTypeScriptEventsByYear(year)
            Topics.UX -> networkService.getUXEventsByYear(year)
            else -> Single.just(emptyList())
        }
    }


}