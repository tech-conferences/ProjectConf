package io.rot.labs.projectconf.data.remote

import io.reactivex.Single
import io.rot.labs.projectconf.data.model.Event
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class FakeNetworkService : NetworkService {
    override fun getAndroidEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(
            listOf(
                Event(
                    "Android Makers",
                    "https://androidmakers.fr",
                    TimeDateUtils.getCurrentDate(),
                    TimeDateUtils.getLastDateOfCurrentMonth(),
                    "Online",
                    "Online",
                    "https://cfp.androidmakers.fr",
                    TimeDateUtils.getFirstDayOfYear(year),
                    "@AndroidMakersFR"
                )
            )
        )
    }

    override fun getClojureEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getCppEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getCssEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getDataEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getDevOpsEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getDotNetEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getElixirEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getElmEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getGeneralEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getGoLangEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getGraphqlEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getGroovyEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getIosEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getIotEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getJavaEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getJavaScriptEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getKotlinEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getLeadershipEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getNetworkingEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getPHPEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getProductEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getPythonEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getRubyEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getRustEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getScalaEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getSecurityEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getTechCommEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getTypeScriptEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }

    override fun getUXEventsByYear(year: Int): Single<List<Event>> {
        return Single.just(listOf())
    }
}