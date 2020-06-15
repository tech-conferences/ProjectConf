package io.rot.labs.projectconf.utils

import io.rot.labs.projectconf.data.local.db.entity.EventEntity
import io.rot.labs.projectconf.data.model.Event
import io.rot.labs.projectconf.data.model.EventHeader
import io.rot.labs.projectconf.utils.common.TimeDateUtils
import java.util.Date

object TestHelper {
    const val milliSecondsIn1Day = 86400000

    val baseTime = System.currentTimeMillis()

    val eventDatePast = baseTime - milliSecondsIn1Day
    val eventDatePast1 = baseTime - 2 * milliSecondsIn1Day

    val fakeEventEntityList = listOf(
        EventEntity(
            Event(
                "Pragma Conf",
                "https://pragmaconf.tech",
                Date(eventDatePast),
                Date(),
                "Allahabad",
                "India"
            ),
            "ux",
            TimeDateUtils.getYearForDate(Date(eventDatePast))
        ), EventEntity(
            Event(
                "Kotliners Conf",
                "https://kotliners.tech",
                Date(eventDatePast1),
                Date(),
                "Austin",
                "U.S.A."
            ),
            "kotlin",
            TimeDateUtils.getYearForDate(Date(eventDatePast1))
        )
    )

    val fakeEventItemList = listOf(
        EventHeader(TimeDateUtils.getEventPeriod(Date(eventDatePast))),
        fakeEventEntityList[0],
        fakeEventEntityList[1]
    )
}
