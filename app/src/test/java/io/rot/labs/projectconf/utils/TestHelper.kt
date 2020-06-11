package io.rot.labs.projectconf.utils

import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.model.Event
import io.rot.labs.projectconf.data.model.EventHeader
import io.rot.labs.projectconf.data.model.EventItem
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import java.util.Date

object TestHelper {
    private const val milliSecondsIn1Day = 86400000

    val fakeEventEntityList = listOf(
        EventEntity(
            Event(
                "Pragma Conf",
                "https://pragmaconf.tech",
                Date(System.currentTimeMillis() - milliSecondsIn1Day),
                Date(),
                "Allahabad",
                "India"
            ),
            "Design"
        ), EventEntity(
            Event(
                "Kotliners Conf",
                "https://kotliners.tech",
                Date(System.currentTimeMillis() - 2 * milliSecondsIn1Day),
                Date(),
                "Austin",
                "U.S.A."
            ),
            "Kotlin"
        )
    )

    val fakeEventBaseList = listOf<EventItem>(
        EventHeader(TimeDateUtils.getEventPeriod(Date(System.currentTimeMillis() - milliSecondsIn1Day))),
        fakeEventEntityList[0],
        fakeEventEntityList[1]
    )
}
