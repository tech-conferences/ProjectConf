package io.rot.labs.projectconf.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.model.Event
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import java.util.*

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvents(events: List<EventEntity>): Completable

    @Query("SELECT * FROM events WHERE startDate >= :date ORDER BY startDate")
    fun getUpComingEventsFromCurrentYear(date: Date): Single<List<EventEntity>>

    @Query("SELECT * FROM events WHERE startDate >= :firstDay AND startDate <= :lastDay")
    fun getEventsByYear(firstDay: Date, lastDay: Date): Single<List<EventEntity>>
}
