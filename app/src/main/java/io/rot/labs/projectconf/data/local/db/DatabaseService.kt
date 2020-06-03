package io.rot.labs.projectconf.data.local.db

import io.reactivex.Completable
import io.reactivex.Single
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseService @Inject constructor (database: ConfDatabase) {

    private val dao = database.eventsDao()

    fun insertEvents(list: List<EventEntity>): Completable {
        return dao.insertEvents(list)
    }

    fun getUpComingEventsFromCurrentYear(): Single<List<EventEntity>> {
        return dao.getUpComingEventsFromCurrentYear()
    }

    fun getEventsForYear(year: Int): Single<List<EventEntity>> {
        return dao.getEventsByYear(
            TimeDateUtils.getFirstDayOfYear(year),
            TimeDateUtils.getLastDayOfYear(year)
        )
    }

}