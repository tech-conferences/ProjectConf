package io.rot.labs.projectconf.data.local.db

import io.reactivex.Completable
import io.reactivex.Single
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.utils.testHelper.AndroidTestHelper
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class FakeDatabaseService(database: ConfDatabase) : DatabaseService(database) {

    override fun insertEvents(list: List<EventEntity>): Completable {
        return Completable.complete()
    }

    override fun getUpComingEventsFromCurrentYear(): Single<List<EventEntity>> {
        return Single.just(AndroidTestHelper.fakeEventEntityList)
    }

    override fun getEventsForYear(year: Int): Single<List<EventEntity>> {
        return Single.just(AndroidTestHelper.fakeEventEntityList)
    }

    override fun getUpComingEventsForCurrentMonth(): Single<List<EventEntity>> {
        return Single.just(AndroidTestHelper.fakeEventEntityList)
    }
}