package io.rot.labs.projectconf.data.local.db

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.Completable
import io.reactivex.Single
import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.utils.testHelper.AndroidTestHelper
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import javax.inject.Singleton

@Singleton
class FakeDatabaseService(database: ConfDatabase) : DatabaseService(database) {

    var toThrowConnectException = false
    var toThrowTimeOutException = false
    var toThrowHttpException = false

    companion object {
        private const val COULD_NOT_CONNECT = "Could not connect"

        private const val TIMEOUT = "timeout"
    }


    override fun insertEvents(list: List<EventEntity>): Completable {
        return Completable.complete()
    }

    override fun getUpComingEventsFromCurrentYear(): Single<List<EventEntity>> {

        return Single.just(AndroidTestHelper.fakeEventEntityList).flatMap {
            when {
                toThrowConnectException -> {
                    throw ConnectException(COULD_NOT_CONNECT)
                }
                toThrowTimeOutException -> {
                    throw SocketTimeoutException(TIMEOUT)
                }
                toThrowHttpException -> {
                    throw  HttpException(
                        Response.error<Any>(
                            HttpURLConnection.HTTP_NOT_FOUND,
                            ResponseBody.create(null, "")
                        )
                    )
                }
                else -> {
                    Single.just(it)
                }
            }
        }
    }

    override fun getEventsForYear(year: Int): Single<List<EventEntity>> {
        return Single.just(AndroidTestHelper.fakeEventEntityList).flatMap {
            when {
                toThrowConnectException -> {
                    throw ConnectException(COULD_NOT_CONNECT)
                }
                toThrowTimeOutException -> {
                    throw SocketTimeoutException(TIMEOUT)
                }
                toThrowHttpException -> {
                    throw  HttpException(
                        Response.error<Any>(
                            HttpURLConnection.HTTP_NOT_FOUND,
                            ResponseBody.create(null, "")
                        )
                    )
                }
                else -> {
                    Single.just(it)
                }
            }
        }
    }

    override fun getUpComingEventsForCurrentMonth(): Single<List<EventEntity>> {
        return Single.just(AndroidTestHelper.fakeEventEntityList).flatMap {
            when {
                toThrowConnectException -> {
                    throw ConnectException(COULD_NOT_CONNECT)
                }
                toThrowTimeOutException -> {
                    throw SocketTimeoutException(TIMEOUT)
                }
                toThrowHttpException -> {
                    throw  HttpException(
                        Response.error<Any>(
                            HttpURLConnection.HTTP_NOT_FOUND,
                            ResponseBody.create(null, "")
                        )
                    )
                }
                else -> {
                    Single.just(it)
                }
            }
        }
    }
}
