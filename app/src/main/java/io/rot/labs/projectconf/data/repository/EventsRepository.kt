package io.rot.labs.projectconf.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.rot.labs.projectconf.data.local.db.DatabaseService
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.model.Event
import io.rot.labs.projectconf.data.remote.NetworkService
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.common.Topics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsRepository @Inject constructor(
    private val databaseService: DatabaseService,
    private val networkService: NetworkService
) {

    private fun getConfSourcesList(year: Int): List<Single<List<EventEntity>>> {

        return when (year) {
            2014 -> listOf(
                networkService.getJavaScriptEventsByYear(year)
                    .mapToListOfEventEntity(Topics.JAVASCRIPT),
                networkService.getRubyEventsByYear(year).mapToListOfEventEntity(Topics.RUBY),
                networkService.getUXEventsByYear(year).mapToListOfEventEntity(Topics.UX)
            )

            2015 -> listOf(
                networkService.getJavaScriptEventsByYear(year)
                    .mapToListOfEventEntity(Topics.JAVASCRIPT),
                networkService.getRubyEventsByYear(year).mapToListOfEventEntity(Topics.RUBY),
                networkService.getUXEventsByYear(year).mapToListOfEventEntity(Topics.UX)
            )

            2016 -> listOf(
                networkService.getJavaScriptEventsByYear(year)
                    .mapToListOfEventEntity(Topics.JAVASCRIPT),
                networkService.getRubyEventsByYear(year).mapToListOfEventEntity(Topics.RUBY),
                networkService.getUXEventsByYear(year).mapToListOfEventEntity(Topics.UX)
            )

            2017 -> listOf(
                networkService.getAndroidEventsByYear(year).mapToListOfEventEntity(Topics.ANDROID),
                networkService.getCssEventsByYear(year).mapToListOfEventEntity(Topics.CSS),
                networkService.getDataEventsByYear(year).mapToListOfEventEntity(Topics.DATA),
                networkService.getDevOpsEventsByYear(year).mapToListOfEventEntity(Topics.DEVOPS),
                networkService.getGeneralEventsByYear(year).mapToListOfEventEntity(Topics.GENERAL),
                networkService.getIosEventsByYear(year).mapToListOfEventEntity(Topics.IOS),
                networkService.getJavaScriptEventsByYear(year)
                    .mapToListOfEventEntity(Topics.JAVASCRIPT),
                networkService.getPHPEventsByYear(year).mapToListOfEventEntity(Topics.PHP),
                networkService.getRubyEventsByYear(year).mapToListOfEventEntity(Topics.RUBY),
                networkService.getTechCommEventsByYear(year)
                    .mapToListOfEventEntity(Topics.TECH_COMM),
                networkService.getUXEventsByYear(year).mapToListOfEventEntity(Topics.UX)
            )

            2018 -> listOf(
                networkService.getAndroidEventsByYear(year).mapToListOfEventEntity(Topics.ANDROID),
                networkService.getCssEventsByYear(year).mapToListOfEventEntity(Topics.CSS),
                networkService.getDataEventsByYear(year).mapToListOfEventEntity(Topics.DATA),
                networkService.getDevOpsEventsByYear(year).mapToListOfEventEntity(Topics.DEVOPS),
                networkService.getDotNetEventsByYear(year).mapToListOfEventEntity(Topics.DOTNET),
                networkService.getElixirEventsByYear(year).mapToListOfEventEntity(Topics.ELIXIR),
                networkService.getGeneralEventsByYear(year).mapToListOfEventEntity(Topics.GENERAL),
                networkService.getGoLangEventsByYear(year).mapToListOfEventEntity(Topics.GOLANG),
                networkService.getGraphqlEventsByYear(year).mapToListOfEventEntity(Topics.GRAPHQL),
                networkService.getIosEventsByYear(year).mapToListOfEventEntity(Topics.IOS),
                networkService.getJavaScriptEventsByYear(year)
                    .mapToListOfEventEntity(Topics.JAVASCRIPT),
                networkService.getPHPEventsByYear(year).mapToListOfEventEntity(Topics.PHP),
                networkService.getPythonEventsByYear(year).mapToListOfEventEntity(Topics.PYTHON),
                networkService.getRubyEventsByYear(year).mapToListOfEventEntity(Topics.RUBY),
                networkService.getRustEventsByYear(year).mapToListOfEventEntity(Topics.RUST),
                networkService.getScalaEventsByYear(year).mapToListOfEventEntity(Topics.SCALA),
                networkService.getSecurityEventsByYear(year)
                    .mapToListOfEventEntity(Topics.SECURITY),
                networkService.getTechCommEventsByYear(year)
                    .mapToListOfEventEntity(Topics.TECH_COMM),
                networkService.getUXEventsByYear(year).mapToListOfEventEntity(Topics.UX)
            )

            2019 -> listOf(
                networkService.getAndroidEventsByYear(year).mapToListOfEventEntity(Topics.ANDROID),
                networkService.getClojureEventsByYear(year).mapToListOfEventEntity(Topics.CLOJURE),
                networkService.getCppEventsByYear(year).mapToListOfEventEntity(Topics.CPP),
                networkService.getCssEventsByYear(year).mapToListOfEventEntity(Topics.CSS),
                networkService.getDataEventsByYear(year).mapToListOfEventEntity(Topics.DATA),
                networkService.getDevOpsEventsByYear(year).mapToListOfEventEntity(Topics.DEVOPS),
                networkService.getDotNetEventsByYear(year).mapToListOfEventEntity(Topics.DOTNET),
                networkService.getElixirEventsByYear(year).mapToListOfEventEntity(Topics.ELIXIR),
                networkService.getElmEventsByYear(year).mapToListOfEventEntity(Topics.ELM),
                networkService.getGeneralEventsByYear(year).mapToListOfEventEntity(Topics.GENERAL),
                networkService.getGoLangEventsByYear(year).mapToListOfEventEntity(Topics.GOLANG),
                networkService.getGraphqlEventsByYear(year).mapToListOfEventEntity(Topics.GRAPHQL),
                networkService.getGroovyEventsByYear(year).mapToListOfEventEntity(Topics.GROOVY),
                networkService.getIosEventsByYear(year).mapToListOfEventEntity(Topics.IOS),
                networkService.getJavaEventsByYear(year).mapToListOfEventEntity(Topics.JAVA),
                networkService.getJavaScriptEventsByYear(year)
                    .mapToListOfEventEntity(Topics.JAVASCRIPT),
                networkService.getLeadershipEventsByYear(year)
                    .mapToListOfEventEntity(Topics.LEADERSHIP),
                networkService.getNetworkingEventsByYear(year)
                    .mapToListOfEventEntity(Topics.NETWORKING),
                networkService.getPHPEventsByYear(year).mapToListOfEventEntity(Topics.PHP),
                networkService.getProductEventsByYear(year).mapToListOfEventEntity(Topics.PRODUCT),
                networkService.getPythonEventsByYear(year).mapToListOfEventEntity(Topics.PYTHON),
                networkService.getRustEventsByYear(year).mapToListOfEventEntity(Topics.RUST),
                networkService.getScalaEventsByYear(year).mapToListOfEventEntity(Topics.SCALA),
                networkService.getSecurityEventsByYear(year)
                    .mapToListOfEventEntity(Topics.SECURITY),
                networkService.getTechCommEventsByYear(year)
                    .mapToListOfEventEntity(Topics.TECH_COMM),
                networkService.getTypeScriptEventsByYear(year)
                    .mapToListOfEventEntity(Topics.TYPESCRIPT),
                networkService.getUXEventsByYear(year).mapToListOfEventEntity(Topics.UX)
            )

            2020 -> listOf(
                networkService.getAndroidEventsByYear(year).mapToListOfEventEntity(Topics.ANDROID),
                networkService.getClojureEventsByYear(year).mapToListOfEventEntity(Topics.CLOJURE),
                networkService.getCppEventsByYear(year).mapToListOfEventEntity(Topics.CPP),
                networkService.getCssEventsByYear(year).mapToListOfEventEntity(Topics.CSS),
                networkService.getDataEventsByYear(year).mapToListOfEventEntity(Topics.DATA),
                networkService.getDevOpsEventsByYear(year).mapToListOfEventEntity(Topics.DEVOPS),
                networkService.getDotNetEventsByYear(year).mapToListOfEventEntity(Topics.DOTNET),
                networkService.getElixirEventsByYear(year).mapToListOfEventEntity(Topics.ELIXIR),
                networkService.getElmEventsByYear(year).mapToListOfEventEntity(Topics.ELM),
                networkService.getGeneralEventsByYear(year).mapToListOfEventEntity(Topics.GENERAL),
                networkService.getGoLangEventsByYear(year).mapToListOfEventEntity(Topics.GOLANG),
                networkService.getGraphqlEventsByYear(year).mapToListOfEventEntity(Topics.GRAPHQL),
                networkService.getIosEventsByYear(year).mapToListOfEventEntity(Topics.IOS),
                networkService.getIotEventsByYear(year).mapToListOfEventEntity(Topics.IOT),
                networkService.getJavaEventsByYear(year).mapToListOfEventEntity(Topics.JAVA),
                networkService.getJavaScriptEventsByYear(year)
                    .mapToListOfEventEntity(Topics.JAVASCRIPT),
                networkService.getKotlinEventsByYear(year).mapToListOfEventEntity(Topics.KOTLIN),
                networkService.getLeadershipEventsByYear(year)
                    .mapToListOfEventEntity(Topics.LEADERSHIP),
                networkService.getNetworkingEventsByYear(year)
                    .mapToListOfEventEntity(Topics.NETWORKING),
                networkService.getPHPEventsByYear(year).mapToListOfEventEntity(Topics.PHP),
                networkService.getProductEventsByYear(year).mapToListOfEventEntity(Topics.PRODUCT),
                networkService.getPythonEventsByYear(year).mapToListOfEventEntity(Topics.PYTHON),
                networkService.getRubyEventsByYear(year).mapToListOfEventEntity(Topics.RUBY),
                networkService.getRustEventsByYear(year).mapToListOfEventEntity(Topics.RUST),
                networkService.getScalaEventsByYear(year).mapToListOfEventEntity(Topics.SCALA),
                networkService.getSecurityEventsByYear(year)
                    .mapToListOfEventEntity(Topics.SECURITY),
                networkService.getTechCommEventsByYear(year)
                    .mapToListOfEventEntity(Topics.TECH_COMM),
                networkService.getTypeScriptEventsByYear(year)
                    .mapToListOfEventEntity(Topics.TYPESCRIPT),
                networkService.getUXEventsByYear(year).mapToListOfEventEntity(Topics.UX)
            )

            2021 -> listOf(
                networkService.getAndroidEventsByYear(year).mapToListOfEventEntity(Topics.ANDROID),
                networkService.getClojureEventsByYear(year).mapToListOfEventEntity(Topics.CLOJURE),
                networkService.getCssEventsByYear(year).mapToListOfEventEntity(Topics.CSS),
                networkService.getDataEventsByYear(year).mapToListOfEventEntity(Topics.DATA),
                networkService.getDevOpsEventsByYear(year).mapToListOfEventEntity(Topics.DEVOPS),
                networkService.getDotNetEventsByYear(year).mapToListOfEventEntity(Topics.DOTNET),
                networkService.getElixirEventsByYear(year).mapToListOfEventEntity(Topics.ELIXIR),
                networkService.getGeneralEventsByYear(year).mapToListOfEventEntity(Topics.GENERAL),
                networkService.getGoLangEventsByYear(year).mapToListOfEventEntity(Topics.GOLANG),
                networkService.getGraphqlEventsByYear(year).mapToListOfEventEntity(Topics.GRAPHQL),
                networkService.getIosEventsByYear(year).mapToListOfEventEntity(Topics.IOS),
                networkService.getJavaEventsByYear(year).mapToListOfEventEntity(Topics.JAVA),
                networkService.getJavaScriptEventsByYear(year)
                    .mapToListOfEventEntity(Topics.JAVASCRIPT),
                networkService.getNetworkingEventsByYear(year)
                    .mapToListOfEventEntity(Topics.NETWORKING),
                networkService.getPHPEventsByYear(year).mapToListOfEventEntity(Topics.PHP),
                networkService.getProductEventsByYear(year).mapToListOfEventEntity(Topics.PRODUCT),
                networkService.getPythonEventsByYear(year).mapToListOfEventEntity(Topics.PYTHON),
                networkService.getRubyEventsByYear(year).mapToListOfEventEntity(Topics.RUBY),
                networkService.getRustEventsByYear(year).mapToListOfEventEntity(Topics.RUST),
                networkService.getScalaEventsByYear(year).mapToListOfEventEntity(Topics.SCALA),
                networkService.getSecurityEventsByYear(year)
                    .mapToListOfEventEntity(Topics.SECURITY),
                networkService.getTechCommEventsByYear(year)
                    .mapToListOfEventEntity(Topics.TECH_COMM),
                networkService.getUXEventsByYear(year).mapToListOfEventEntity(Topics.UX)
            )

            else -> emptyList()
        }
    }

    fun getEventsOfYear(year: Int): Flowable<List<EventEntity>> {
        val allIn = getConfSourcesList(year)
        return Single.merge(allIn).onBackpressureBuffer()
    }

    fun getUpComingEvents(): Single<List<EventEntity>> {
        val yearList = TimeDateUtils.getConfYearsList()
        val currYear = yearList.last() - 1
        val nextYear = yearList.last()
        val upcomingEventSources = getConfSourcesList(currYear) + getConfSourcesList(nextYear)

        return databaseService.getUpComingEventsFromCurrentYear()
            .flatMap {
                if (it.isEmpty()) {
                    Single.merge(upcomingEventSources).collect(
                        { mutableListOf<EventEntity>() },
                        { collector, value ->
                            value.forEach { event -> collector.add(event) }
                        }
                    ).flatMap { list ->
                        databaseService.insertEvents(list).toSingle { "Complete" }
                    }.flatMap {
                        databaseService.getUpComingEventsFromCurrentYear()
                    }
                } else {
                    Single.just(it)
                }
            }
    }

    fun getUpComingEventsForCurrentMonth(): Single<List<EventEntity>> {
        val yearList = TimeDateUtils.getConfYearsList()
        val currYear = yearList.last() - 1

        val upcomingEventSources = getConfSourcesList(currYear)

        return databaseService.getUpComingEventsForCurrentMonth()
            .flatMap {
                if (it.isEmpty()) {
                    Single.merge(upcomingEventSources).collect(
                        { mutableListOf<EventEntity>() },
                        { collector, value ->
                            value.forEach { event -> collector.add(event) }
                        }
                    ).flatMap { list ->
                        databaseService.insertEvents(list).toSingle { "Complete" }
                    }.flatMap {
                        databaseService.getUpComingEventsForCurrentMonth()
                    }
                } else {
                    Single.just(it)
                }
            }

    }

    fun insertEvents(list: List<EventEntity>): Completable {
        return databaseService.insertEvents(list)
    }


    private fun Single<List<Event>>.mapToListOfEventEntity(topic: String): Single<List<EventEntity>> {
        return this.map {
            it.map { event ->
                EventEntity(event, topic)
            }
        }
    }

}