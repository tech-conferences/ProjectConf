package io.rot.labs.projectconf.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import io.rot.labs.projectconf.data.local.db.EventsDatabaseService
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.model.Event
import io.rot.labs.projectconf.data.remote.NetworkService
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import io.rot.labs.projectconf.utils.common.TopicsList
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsRepository @Inject constructor(
    private val eventsDatabaseService: EventsDatabaseService,
    private val networkService: NetworkService
) {

    private fun getConfSourcesList(year: Int): List<Single<List<EventEntity>>> {
        return makeConfSourceList(year, getTopicList(year))
    }

    fun getUpComingEventsForCurrentMonth(isRefresh: Boolean): Single<List<EventEntity>> {
        val yearList = TimeDateUtils.getConfYearsList()
        val currYear = yearList.last() - 1

        val upcomingEventSources = getConfSourcesList(currYear)

        return eventsDatabaseService.getUpComingEventsForCurrentMonth()
            .flatMap {
                if (it.isEmpty() || isRefresh) {
                    Single.merge(upcomingEventSources).collect(
                        { mutableListOf<EventEntity>() },
                        { collector, value ->
                            value.forEach { event -> collector.add(event) }
                        }
                    ).flatMap { list ->
                        eventsDatabaseService.insertEvents(list).toSingle { "Complete" }
                    }.flatMap {
                        eventsDatabaseService.getUpComingEventsForCurrentMonth()
                    }
                } else {
                    Single.just(it)
                }
            }
    }

    fun getUpComingEventsForCurrentYear(): Single<String> {
        val yearList = TimeDateUtils.getConfYearsList()
        val currYear = yearList.last() - 1

        val upcomingEventSources = getConfSourcesList(currYear)

        return Single.merge(upcomingEventSources).collect(
            { mutableListOf<EventEntity>() },
            { collector, value ->
                value.forEach { event -> collector.add(event) }
            }
        ).flatMap { list ->
            eventsDatabaseService.insertEvents(list).toSingle { "Complete" }
        }
    }

    fun getUpComingEventsForTech(
        topics: List<String>,
        isRefresh: Boolean
    ): Single<List<EventEntity>> {
        val yearList = TimeDateUtils.getConfYearsList()
        val nextYear = yearList.last()
        val currYear = nextYear - 1
        return eventsDatabaseService.getEventsForYearAndTech(topics, nextYear)
            .flatMap {
                if (it.isEmpty() || isRefresh) {
                    val upcomingSources =
                        makeConfSourceList(
                            currYear,
                            topics.intersect(getTopicList(currYear)).toList()
                        ) + makeConfSourceList(
                            nextYear,
                            topics.intersect(getTopicList(nextYear)).toList()
                        )
                    Single.merge(upcomingSources).collect(
                        { mutableListOf<EventEntity>() },
                        { collector, value ->
                            value.forEach { event -> collector.add(event) }
                        }
                    ).flatMap { list ->
                        eventsDatabaseService.insertEvents(list).toSingle { "Complete" }
                    }.flatMap {
                        eventsDatabaseService.getUpComingEventsForTech(topics)
                    }
                } else {
                    eventsDatabaseService.getUpComingEventsForTech(topics)
                }
            }
    }

    fun getEventsForYearAndTech(
        tech: String,
        year: Int,
        isRefresh: Boolean
    ): Single<List<EventEntity>> {

        val isCurrYear = year == TimeDateUtils.getConfYearsList().last() - 1

        return eventsDatabaseService.getEventsForYearAndTech(listOf(tech), year)
            .flatMap {
                if (it.isEmpty() || isRefresh) {
                    val source = makeConfSourceList(year, listOf(tech))
                    Single.merge(source).collect(
                        { mutableListOf<EventEntity>() },
                        { collector, value ->
                            value.forEach { event -> collector.add(event) }
                        }
                    ).flatMap { list ->
                        eventsDatabaseService.insertEvents(list).toSingle { "Complete" }
                    }.flatMap {
                        if (isCurrYear) {
                            eventsDatabaseService.getPastEventsForCurrentYearAndTech(
                                listOf(tech),
                                year
                            )
                        } else {
                            eventsDatabaseService.getEventsForYearAndTech(listOf(tech), year)
                        }
                    }
                } else {
                    if (isCurrYear) {
                        eventsDatabaseService.getPastEventsForCurrentYearAndTech(listOf(tech), year)
                    } else {
                        eventsDatabaseService.getEventsForYearAndTech(listOf(tech), year)
                    }
                }
            }
    }

    fun getUpcomingEventsForUserTech(tech: List<String>): Single<List<EventEntity>> {
        val yearList = TimeDateUtils.getConfYearsList()
        val currYear = yearList.last() - 1
        val nextYear = yearList.last()

        val upcomingEventSources =
            makeConfSourceList(currYear, tech.intersect(getTopicList(currYear)).toList()) +
                    makeConfSourceList(nextYear, tech.intersect(getTopicList(nextYear)).toList())

        return Single.merge(upcomingEventSources).collect(
            { mutableListOf<EventEntity>() },
            { collector, value ->
                value.forEach { event -> collector.add(event) }
            }
        ).flatMap { networkList ->
            val localSource = listOf(
                eventsDatabaseService.getEventsForYearAndTech(tech, currYear),
                eventsDatabaseService.getEventsForYearAndTech(tech, nextYear)
            )
            Single.merge(localSource).collect(
                { mutableListOf<EventEntity>() },
                { collector, value ->
                    value.forEach { event -> collector.add(event) }
                }
            ).flatMap { localList ->
                Single.just(networkList - localList)
            }.flatMap {
                eventsDatabaseService.insertEvents(it).toSingle { it }
            }
        }
    }

    fun getUpComingEventsForUserTechCurrentMonth(topics: List<String>): Single<List<EventEntity>> {
        return eventsDatabaseService.getUpComingEventsForUserTechCurrentMonth(topics)
    }

    fun insertEvents(list: List<EventEntity>): Completable {
        return eventsDatabaseService.insertEvents(list)
    }

    fun getEventDetails(name: String, startDate: Date, topic: String): Single<EventEntity> {
        return eventsDatabaseService.getEventDetails(name, startDate, topic)
    }

    fun getRecentEventsByQuery(nameQuery: String, yearList: List<Int>): Single<List<EventEntity>> {
        return eventsDatabaseService.getEventsForYear(yearList[1])
            .flatMap {
                if (it.isEmpty()) {
                    val source = getConfSourcesList(yearList[1])
                    Single.merge(source).collect(
                        { mutableListOf<EventEntity>() },
                        { collector, value ->
                            value.forEach { event -> collector.add(event) }
                        }
                    ).flatMap { list ->
                        eventsDatabaseService.insertEvents(list).toSingle { "Complete" }
                    }.flatMap {
                        eventsDatabaseService.getEventsByQuery(nameQuery, yearList)
                    }
                } else {
                    eventsDatabaseService.getEventsByQuery(nameQuery, yearList)
                }
            }
    }

    private fun makeConfSourceList(year: Int, list: List<String>): List<Single<List<EventEntity>>> {
        val sourceList = mutableListOf<Single<List<EventEntity>>>()
        list.forEach {
            sourceList.add(networkService.getEventsByYear(year, it).mapToListOfEventEntity(it))
        }
        return sourceList
    }

    private fun Single<List<Event>>.mapToListOfEventEntity(topic: String): Single<List<EventEntity>> {
        return this.map {
            it.map { event ->
                EventEntity(event, topic, TimeDateUtils.getYearForDate(event.startDate))
            }
        }
    }

    private fun getTopicList(year: Int): List<String> {
        return when (year) {
            2014, 2015 -> TopicsList.YEAR_2014_2015
            2016 -> TopicsList.YEAR_2016
            2017 -> TopicsList.YEAR_2017
            2018 -> TopicsList.YEAR_2018
            2019 -> TopicsList.YEAR_2019
            2020 -> TopicsList.YEAR_2020
            2021 -> TopicsList.YEAR_2021
            else -> emptyList()
        }
    }
}
