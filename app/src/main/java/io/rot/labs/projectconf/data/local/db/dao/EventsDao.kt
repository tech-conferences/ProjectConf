package io.rot.labs.projectconf.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import io.rot.labs.projectconf.data.model.Event
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import java.util.*

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvents(events: List<Event>): Single<List<Long>>

    @Query("SELECT * FROM events WHERE startDate >= :date")
    fun getUpComingEventsFromCurrentYear(date: Date = TimeDateUtils.getCurrentDate()): Single<List<Event>>

    @Query("SELECT * FROM events WHERE startDate >= :firstDay AND startDate <= :lastDay")
    fun getEventsByYear(
        firstDay: Date = TimeDateUtils.getFirstDayOfCurrentYear(),
        lastDay: Date = TimeDateUtils.getLastDayOfCurrentYear()
    ): Single<List<Event>>
}
