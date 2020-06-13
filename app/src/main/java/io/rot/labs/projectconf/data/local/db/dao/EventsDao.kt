package io.rot.labs.projectconf.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import java.util.Date

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvents(events: List<EventEntity>): Completable

    @Query("SELECT * FROM events WHERE startDate >= :firstDay AND startDate <= :lastDay ORDER BY startDate")
    fun getEventsByYear(firstDay: Date, lastDay: Date): Single<List<EventEntity>>

    @Query("SELECT * FROM events WHERE startDate >= :firstDay AND startDate <= :lastDay AND topic LIKE :topic ORDER BY startDate")
    fun getEventsByYearAndTech(
        firstDay: Date,
        lastDay: Date,
        topic: String
    ): Single<List<EventEntity>>

    @Query("SELECT * FROM events WHERE startDate >= :date ORDER BY startDate")
    fun getUpComingEventsFromCurrentYear(date: Date): Single<List<EventEntity>>

    @Query("SELECT * from events WHERE startDate >= :date AND startDate <= :monthEnd ORDER BY startDate")
    fun getUpComingEventsForCurrentMonth(date: Date, monthEnd: Date): Single<List<EventEntity>>

    @Query("SELECT * FROM events WHERE startDate >= :date AND topic IN (:topics) ORDER BY startDate")
    fun getUpComingEventsForTech(topics: List<String>, date: Date): Single<List<EventEntity>>

    @Query("SELECT * FROM events WHERE startDate LIKE :startDate AND name LIKE :name LIMIT 1")
    fun getEventDetails(name: String, startDate: Date): Single<EventEntity>
}
