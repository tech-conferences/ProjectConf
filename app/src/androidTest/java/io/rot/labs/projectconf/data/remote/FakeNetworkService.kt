package io.rot.labs.projectconf.data.remote

import io.reactivex.Single
import io.rot.labs.projectconf.data.model.Event
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import javax.inject.Singleton

@Singleton
class FakeNetworkService : NetworkService {
    override fun getEventsByYear(year: Int, tech: String): Single<List<Event>> {
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
}
