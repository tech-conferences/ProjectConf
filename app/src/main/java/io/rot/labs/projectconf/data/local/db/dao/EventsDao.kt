package io.rot.labs.projectconf.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Single
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import java.util.Date

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvents(events: List<EventEntity>): Completable

    @Update
    fun updateEvents(events: List<EventEntity>): Completable

    @Query("SELECT * FROM events WHERE startDate >= :firstDay AND startDate <= :lastDay ORDER BY startDate")
    fun getEventsByYear(firstDay: Date, lastDay: Date): Single<List<EventEntity>>

    @Query("SELECT * FROM events WHERE startDate >= :firstDay AND startDate <= :lastDay AND topic IN (:topics) ORDER BY startDate")
    fun getEventsByYearAndTech(
        firstDay: Date,
        lastDay: Date,
        topics: List<String>
    ): Single<List<EventEntity>>

    @Query("SELECT * FROM events WHERE startDate >= :date ORDER BY startDate")
    fun getUpComingEventsFromCurrentYear(date: Date): Single<List<EventEntity>>

    @Query("SELECT * from events WHERE startDate >= :date AND startDate <= :monthEnd ORDER BY startDate")
    fun getUpComingEventsForCurrentMonth(date: Date, monthEnd: Date): Single<List<EventEntity>>

    @Query("SELECT * from events WHERE startDate >= :date AND startDate <= :monthEnd AND topic IN (:topics) ORDER BY startDate")
    fun getUpComingEventsForUserTechCurrentMonth(
        date: Date,
        monthEnd: Date,
        topics: List<String>
    ): Single<List<EventEntity>>

    @Query("SELECT * FROM events WHERE startDate >= :date AND topic IN (:topics) ORDER BY startDate")
    fun getUpComingEventsForTech(topics: List<String>, date: Date): Single<List<EventEntity>>

    @Query("SELECT * FROM events WHERE startDate LIKE :startDate AND name LIKE :name AND topic LIKE :topic LIMIT 1")
    fun getEventDetails(name: String, startDate: Date, topic: String): Single<EventEntity>

    @Query("SELECT * FROM events WHERE name LIKE :nameQuery AND year IN (:yearList) ORDER BY startDate DESC")
    fun getEventsByQuery(nameQuery: String, yearList: List<Int>): Single<List<EventEntity>>
}
