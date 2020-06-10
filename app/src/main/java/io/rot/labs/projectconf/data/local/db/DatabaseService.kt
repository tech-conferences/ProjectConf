package io.rot.labs.projectconf.data.local.db

import io.reactivex.Completable
import io.reactivex.Single
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import javax.inject.Singleton

@Singleton
open class DatabaseService(database: ConfDatabase) {

    private val dao = database.eventsDao()

    open fun insertEvents(list: List<EventEntity>): Completable {
        return dao.insertEvents(list)
    }

    open fun getUpComingEventsFromCurrentYear(): Single<List<EventEntity>> {
        return dao.getUpComingEventsFromCurrentYear(TimeDateUtils.getCurrentDate())
    }

    open fun getEventsForYear(year: Int): Single<List<EventEntity>> {
        return dao.getEventsByYear(
            TimeDateUtils.getFirstDayOfYear(year),
            TimeDateUtils.getLastDayOfYear(year)
        )
    }

    open fun getUpComingEventsForCurrentMonth(): Single<List<EventEntity>> {
        return dao.getUpComingEventsForCurrentMonth(
            TimeDateUtils.getCurrentDate(),
            TimeDateUtils.getLastDateOfCurrentMonth()
        )
    }
}
