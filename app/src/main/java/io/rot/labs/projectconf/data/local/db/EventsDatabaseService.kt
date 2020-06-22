package io.rot.labs.projectconf.data.local.db

import io.reactivex.Completable
import io.reactivex.Single
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class EventsDatabaseService @Inject constructor(database: ConfDatabase) {

    private val eventsDao = database.eventsDao()

    open fun insertEvents(list: List<EventEntity>): Completable {
        return eventsDao.insertEvents(list)
    }

    open fun updateEvents(list: List<EventEntity>): Completable {
        return eventsDao.updateEvents(list)
    }

    open fun getEventsForYear(year: Int): Single<List<EventEntity>> {
        return eventsDao.getEventsByYear(
            TimeDateUtils.getFirstDayOfYear(year),
            TimeDateUtils.getLastDayOfYear(year)
        )
    }

    open fun getEventsForYearAndTech(topics: List<String>, year: Int): Single<List<EventEntity>> {
        return eventsDao.getEventsByYearAndTech(
            TimeDateUtils.getFirstDayOfYear(year),
            TimeDateUtils.getLastDayOfYear(year),
            topics
        )
    }

    open fun getPastEventsForCurrentYearAndTech(
        topics: List<String>,
        year: Int
    ): Single<List<EventEntity>> {
        return eventsDao.getEventsByYearAndTech(
            TimeDateUtils.getFirstDayOfYear(year),
            TimeDateUtils.getCurrentDate(),
            topics
        )
    }

    open fun getUpComingEventsForCurrentMonth(): Single<List<EventEntity>> {
        return eventsDao.getUpComingEventsForCurrentMonth(
            TimeDateUtils.getCurrentDate(),
            TimeDateUtils.getLastDateOfCurrentMonth()
        )
    }

    open fun getUpComingEventsForUserTechCurrentMonth(topics: List<String>): Single<List<EventEntity>> {
        return eventsDao.getUpComingEventsForUserTechCurrentMonth(
            TimeDateUtils.getCurrentDate(),
            TimeDateUtils.getLastDateOfCurrentMonth(),
            topics
        )
    }

    open fun getUpComingEventsForTech(topics: List<String>): Single<List<EventEntity>> {
        return eventsDao.getUpComingEventsForTech(topics, TimeDateUtils.getCurrentDate())
    }

    open fun getUpComingEventsFromCurrentYear(): Single<List<EventEntity>> {
        return eventsDao.getUpComingEventsFromCurrentYear(TimeDateUtils.getCurrentDate())
    }

    open fun getEventDetails(name: String, startDate: Date, topic: String): Single<EventEntity> {
        return eventsDao.getEventDetails(name, startDate, topic)
    }

    open fun getEventsByQuery(nameQuery: String, yearList: List<Int>): Single<List<EventEntity>> {
        val mutatedNamedQuery = "%$nameQuery%"
        return eventsDao.getEventsByQuery(mutatedNamedQuery, yearList)
    }
}
