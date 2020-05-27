package io.rot.labs.projectconf.data.repository

import io.reactivex.Flowable
import io.reactivex.Single
import io.rot.labs.projectconf.data.local.db.DatabaseService
import io.rot.labs.projectconf.data.model.Event
import io.rot.labs.projectconf.data.remote.NetworkService
import io.rot.labs.projectconf.utils.common.TimeDateUtils
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

            else -> emptyList()
        }
    }

    fun getEventsOfYear(year: Int): Flowable<List<Event>> {
        val allIn = getConfSourcesList(year)
        return Single.merge(allIn)
    }

    fun getUpComingEvents(): Single<List<Event>> {
        return databaseService.eventsDao().getUpComingEventsFromCurrentYear()
    }

    fun insertEventsToLocal(list: List<Event>): Single<List<Long>> {
        return databaseService.eventsDao().insertEvents(list)
    }


}